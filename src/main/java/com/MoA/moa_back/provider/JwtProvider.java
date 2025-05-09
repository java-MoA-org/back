package com.MoA.moa_back.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Random;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.MoA.moa_back.common.enums.JwtErrorCode;
import com.MoA.moa_back.common.enums.UserRole;
import com.MoA.moa_back.handler.CustomJwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Component
@Getter
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private int ADMIN_EXPIRE_MIN_TIME = 60 * 24;
    private int ADMIN_EXPIRE_SEC_TIME = 60 * 60 * 24;
    private int USER_EXPIRE_MIN_TIME = 30;
    private int USER_EXPIRE_SEC_TIME = 60 * 30;
    private int REFRESH_EXPIRE_MIN_TIME = 60 * 24;
    private int REFRESH_EXPIRE_SEC_TIME = 60 * 60 * 24;


    private String generateToken(String value, long minutes) {
        Date expiration = Date.from(Instant.now().plus(minutes, ChronoUnit.MINUTES));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS256)
            .setSubject(value)
            .setIssuedAt(new Date())
            .setExpiration(expiration)
            .compact();
    }

    public static String generate6DigitCode() {
        int code = new Random().nextInt(1_000_000); // 0 ~ 999999
        return String.format("%06d", code); // 6자리, 앞에 0 채움
    }


    public String generateVerificationToken(String verifyDetail, String code) {
        long now = System.currentTimeMillis();
        long expirationTime = 10 * 60 * 1000; // 10분
        String detail = verifyDetail.contains("@") ? "email" : "phoneNumber";
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject("email-verification")
                .claim(detail, verifyDetail)
                .claim("code", code)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.");
        }
    }



    public String createVerifyToken(String verifyDetail, String code){
        return generateVerificationToken(verifyDetail, code);
    }
    
    public String createAccessToken(String userId, UserRole userRole) {
        System.out.println(userRole);
        if(userRole.equals(UserRole.ADMIN)){
            System.out.println("admin logined");
            return generateToken(userId, ADMIN_EXPIRE_MIN_TIME);
        }
        return generateToken(userId, USER_EXPIRE_MIN_TIME);
    }
    
    public String createRefreshToken(String userId) {
        return generateToken(userId, ADMIN_EXPIRE_MIN_TIME); // 1일
    }

    public String validate(String jwt){
        String userId = null;
        System.out.println("🔍 [JWT 토큰 디버깅] 입력 토큰: " + jwt);
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        try {
            System.out.println("🔐 [JWT 검증 시도] 토큰: " + jwt);

            userId = Jwts.parserBuilder()
                         .setSigningKey(key)
                         .build()
                         .parseClaimsJws(jwt)
                         .getBody()
                         .getSubject();

            System.out.println("✅ [JWT 유효] userId: " + userId);
        } catch (ExpiredJwtException e) {
            System.out.println("❌ [JWT 만료] " + e.getMessage());
            throw new CustomJwtException("토큰이 만료되었습니다.", JwtErrorCode.EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("❌ [JWT 무효] " + e.getMessage());
            throw new CustomJwtException("유효하지 않은 토큰입니다.", JwtErrorCode.INVALID);
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