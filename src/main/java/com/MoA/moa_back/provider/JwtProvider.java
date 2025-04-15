package com.MoA.moa_back.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private String generateToken(String userId, long minutes) {
        Date expiration = Date.from(Instant.now().plus(minutes, ChronoUnit.MINUTES));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS256)
            .setSubject(userId)
            .setIssuedAt(new Date())
            .setExpiration(expiration)
            .compact();
    }
    
    public String createAccessToken(String userId) {
        return generateToken(userId, 30);
    }
    
    public String createRefreshToken(String userId) {
        return generateToken(userId, 60 * 24); // 1일
    }

    public String validate(String jwt){
        String userId = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        try {
            userId = Jwts.parserBuilder()
                         .setSigningKey(key)
                         .build()
                         .parseClaimsJws(jwt)
                         .getBody()
                         .getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public String extractAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
