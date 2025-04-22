package com.MoA.moa_back.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.provider.JwtProvider;
import com.MoA.moa_back.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// class: JWT Bearer Token 인증 처리를 위한 필터 //
// description: 필터 처리로 인증이 완료되면 접근 주체의 값에는 userId가 주입 //

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        try {
            System.out.println("요청 URI: " + request.getRequestURI());

            String path = request.getRequestURI();

            // 🔓 인증 없이 접근 허용할 경로들
            if (path.startsWith("/profile/file/") ||
                path.startsWith("/board") ||
                path.startsWith("/notice") ||
                path.startsWith("/used-trade") ||
                path.startsWith("/news") ||
                path.startsWith("/home")) {

                filterChain.doFilter(request, response);
                return;
            }

            String token = getToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String userId = jwtProvider.validate(token);
            if (userId == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 사용자 권한 가져오기 (예: ADMIN, USER)
            String userRole = userEntity.getUserRole().name();
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRole);

            // 인증 정보 SecurityContext에 주입
            AbstractAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);

        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    // 헤더에서 Bearer 토큰 추출
    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        return authorization.substring(7);
    }
}