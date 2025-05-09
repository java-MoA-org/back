package com.MoA.moa_back.config;

import com.MoA.moa_back.config.interceptor.JwtChannelInterceptor;
import com.MoA.moa_back.config.interceptor.WebSocketAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtChannelInterceptor jwtChannelInterceptor;

    @Autowired
    private WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(webSocketAuthInterceptor) // JWT 인증용 인터셉터
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new DefaultHandshakeHandler());
                // .withSockJS(); // SockJS 제거 (token 전달 문제 방지)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");         // 클라이언트 → 서버로 보낼 때 경로
        registry.enableSimpleBroker("/topic");                      // 서버 → 구독 클라이언트 전송
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtChannelInterceptor);
    }
}