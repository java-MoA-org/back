package com.MoA.moa_back.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.MoA.moa_back.common.entity.CustomOAuth2User;
import com.MoA.moa_back.provider.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// class: OAuth2 유저 서비스 작업이 성공했을 때 처리를 담당하는 클래스 //
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Value("${oauth.client-main}")
  private String oAuthClientMain;
  @Value("${oauth.client-auth}")
  private String oAuthClientAuth;
  
  private final JwtProvider jwtProvider;

  @Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
    Map<String, Object> attributes = oAuth2User.getAttributes();
    boolean existed = oAuth2User.isExisted();

    
    // description: 회원가입 O //
    if (existed) {
        String accessToken = (String) attributes.get("accessToken");
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setMaxAge(60 * 30);
        
        String userId = oAuth2User.getName();
    
        String refreshToken = jwtProvider.createRefreshToken(userId);
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60 * 60 * 24); // 1일
        response.addCookie(refreshCookie);
        response.addCookie(cookie);
        response.sendRedirect(oAuthClientMain);

        System.out.println(response);
    }
    // description: 회원가입 X //
    else {      
      String joinType = (String) attributes.get("joinType");
      Cookie joinTypeCookie = new Cookie("joinType", joinType);
      joinTypeCookie.setPath("/");
      joinTypeCookie.setHttpOnly(false);
      joinTypeCookie.setMaxAge(60 * 60);
      
      String userId = (String) attributes.get("userId");
      Cookie userIdCookie = new Cookie("userId", userId);
      userIdCookie.setPath("/");
      userIdCookie.setHttpOnly(false);
      userIdCookie.setMaxAge(60 * 60);

      String userPassword = (String) attributes.get("userPassword");
      Cookie userPasswordCookie = new Cookie("userPassword", userPassword);
      userPasswordCookie.setPath("/");
      userPasswordCookie.setHttpOnly(false);
      userPasswordCookie.setMaxAge(60 * 60);
      
      String userNickname = (String) attributes.get("userNickname");
      Cookie userNicknameCookie = new Cookie("userNickname", userNickname);
      userNicknameCookie.setPath("/");
      userNicknameCookie.setHttpOnly(false);
      userNicknameCookie.setMaxAge(60 * 60);
      
      String profileImage = (String) attributes.get("profileImage");
      Cookie profileImageCookie = new Cookie("profileImage", profileImage);
      profileImageCookie.setPath("/");
      profileImageCookie.setHttpOnly(false);
      profileImageCookie.setMaxAge(60 * 60);

      response.addCookie(joinTypeCookie);
      response.addCookie(userIdCookie);
      response.addCookie(userPasswordCookie);
      response.addCookie(userNicknameCookie);
      response.addCookie(profileImageCookie);

      if(joinType.equals("NAVER")){
        String userEmail = (String) attributes.get("userEmail");
        Cookie userEmailCookie = new Cookie("userEmail", userEmail);
        userEmailCookie.setPath("/");
        userEmailCookie.setHttpOnly(false);
        userEmailCookie.setMaxAge(60 * 60);
        
        String userPhoneNumber = (String) attributes.get("userPhoneNumber");
        Cookie userPhoneNumberCookie = new Cookie("userPhoneNumber", userPhoneNumber);
        userPhoneNumberCookie.setPath("/");
        userPhoneNumberCookie.setHttpOnly(false);
        userPhoneNumberCookie.setMaxAge(60 * 60);

        response.addCookie(userEmailCookie);
        response.addCookie(userPhoneNumberCookie);
      }

      response.sendRedirect(oAuthClientAuth);
    }
  }

}