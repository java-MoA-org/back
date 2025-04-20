package com.MoA.moa_back.config;

import java.io.IOException;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.MoA.moa_back.filter.JwtAuthenticationFilter;
import com.MoA.moa_back.handler.OAuth2SuccessHandler;
import com.MoA.moa_back.service.implement.OAuth2ServiceImplement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2ServiceImplement oAuth2Service;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    @Order(0)
    public SecurityFilterChain configure(HttpSecurity security) throws Exception {

        security
            .httpBasic(HttpBasicConfigurer::disable)
            .sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))   
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/profile/file/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/board/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/notice").permitAll()
                
                // 🔽 공지사항 관련 GET 요청 허용 추가
                .requestMatchers(HttpMethod.GET, "/notice").permitAll()
                .requestMatchers(HttpMethod.GET, "/notice/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/notice").permitAll()
                
                .requestMatchers("/api/v1/board/**").permitAll()
                .requestMatchers("/api/v1/daily/**").permitAll()
                .requestMatchers("/api/v1/used-trade/**").permitAll()
                .requestMatchers("/api/news/**").permitAll() 
                .requestMatchers("/home/**").permitAll() 
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/sns"))
                .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2Service))
                .successHandler(oAuth2SuccessHandler)
            )
            .exceptionHandling(exception -> exception
            .authenticationEntryPoint(new AuthenticationFailEntryPoint())
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);;

        return security.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
class AuthenticationFailEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Auth Fail.\" }");
    }

    
}