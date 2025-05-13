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

// 모든 요청마다 1회 실행, jwt 토큰이 있으면 사용자 인증
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

            // 인증 없이 접근 허용할 경로들
            // @AuthenticationPrincipal이 막힘.
            if (path.startsWith("/api/v1/auth/refresh") ||
                path.startsWith("/profile/file/") ||
                path.startsWith("/api/v1/user-page/images/file/upload") ||
                path.startsWith("/api/v1/board") ||
                path.startsWith("/notice") ||
                path.startsWith("/api/v1/used-trade") ||
                path.startsWith("/api/v1/daily") ||
                path.startsWith("/news") ||
                path.startsWith("/home") ||
                (path.startsWith("/api/user/") && path.endsWith("/profile"))) {

                filterChain.doFilter(request, response);
                return;
            }

            // token 추출
            String token = getToken(request);
            System.out.println("[🔐 JWT 필터] Authorization 헤더: " + request.getHeader("Authorization"));
            if (token == null) {
                System.out.println("token이 비었습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            String userId = jwtProvider.validate(token);
            System.out.println("[🔐 JWT 필터] 검증된 userId: " + userId);
            if (userId == null) {
                System.out.println("token으로 가져온 id가 없습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            UserEntity userEntity = userRepository.findByUserId(userId);
            System.out.println("[🔐 JWT 필터] 불러온 유저 닉네임: " + (userEntity != null ? userEntity.getUserNickname() : "null"));
            if (userEntity == null) {
                System.out.println("id로 찾은 유저가 없습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            // 사용자 권한 가져오기 (예: ADMIN, USER)
            String userRole = userEntity.getUserRole().name();
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRole);

            // 인증 정보 생성 및 저장
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
    
    // 요청 헤더에서 토큰 추출
    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        return authorization.substring(7);
    }
}