package com.MoA.moa_back.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.MoA.moa_back.provider.JwtProvider;

import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            String token = null;

            // 1. Authorization 헤더 우선
            String authHeader = httpServletRequest.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }

            // 2. 없으면 쿼리 파라미터 fallback
            if (token == null || token.isEmpty()) {
                token = httpServletRequest.getParameter("token");
            }

            if (token != null && !token.isEmpty()) {
                System.out.println("[WebSocketAuthInterceptor] 받은 토큰: " + token);

                String userId = null;
                try {
                    userId = jwtProvider.validate(token);
                    System.out.println("[WebSocketAuthInterceptor] 파싱된 userId: " + userId);
                } catch (Exception e) {
                    System.out.println("[WebSocketAuthInterceptor] 토큰 검증 실패: " + e.getMessage());
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return false;
                }

                if (userId != null) {
                    attributes.put("userId", userId);  // WebSocket 세션에 유저 ID 저장
                    return true;
                }
            }
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}