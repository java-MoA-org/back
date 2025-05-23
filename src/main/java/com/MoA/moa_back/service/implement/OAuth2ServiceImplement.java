package com.MoA.moa_back.service.implement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.entity.CustomOAuth2User;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.enums.UserRole;
import com.MoA.moa_back.provider.JwtProvider;
import com.MoA.moa_back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2ServiceImplement extends DefaultOAuth2UserService{
    
    private final UserRepository repository;
    private final JwtProvider jwtProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registration = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        String userNickname, userEmail, userPhoneNumber, profileImage;
        Map<String, Object> elements = new HashMap<>();
        CustomOAuth2User customOAuth2User;
        
        String userId = null;
        UserRole userRole = null;
        Map<String, String> response = (Map<String, String>)oAuth2User.getAttributes().get("response");
        if("KAKAO".equals(registration)){
            userId = registration + "_" + attributes.get("id").toString().substring(0, 10);
        }else if("NAVER".equals(registration)){
            userId = registration + "_" + response.get("id").toString().substring(0, 10);
        }else{
            return null;
        }
        UserEntity userEntity = repository.findByUserId(userId);
        
        String userPassword = UUID.randomUUID().toString();
        
        if (userEntity == null) {
            if ("KAKAO".equals(registration)) {
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        
                userNickname = (String) profile.get("nickname");
                profileImage = (String) profile.get("profile_image_url");
        
                elements.put("joinType", "KAKAO");
                elements.put("userId", userId);
                elements.put("userPassword", userPassword);
                elements.put("userNickname", userNickname);
                elements.put("profileImage", profileImage);
                
            } else if ("NAVER".equals(registration)) {
                
                userId = registration + "_" + response.get("id").toString().substring(0, 10);
                userNickname = (String) response.get("nickname");
                userEmail = (String) response.get("email");
                userPhoneNumber = (String) response.get("mobile");
                profileImage = (String) response.get("profile_image");
                
                elements.put("joinType", "NAVER");
                elements.put("userId", userId);
                elements.put("userPassword", userPassword);
                elements.put("userNickname", userNickname);
                elements.put("userEmail", userEmail);
                elements.put("userPhoneNumber", userPhoneNumber);
                elements.put("profileImage", profileImage);
            }
        
            // ❗ CustomOAuth2User 생성
            customOAuth2User = new CustomOAuth2User(userId, elements, false);
        
        } else {
            // 기존 회원인 경우
            userId = userEntity.getUserId();
            userRole = userEntity.getUserRole();
            String accessToken = jwtProvider.createAccessToken(userId, userRole);
            elements.put("accessToken", accessToken);

            customOAuth2User = new CustomOAuth2User(userId, elements, true);
        }
        
        return customOAuth2User;
        
        
    }

}
