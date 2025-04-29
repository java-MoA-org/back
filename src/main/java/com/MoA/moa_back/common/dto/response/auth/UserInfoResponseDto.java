package com.MoA.moa_back.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.entity.UserInterestsEntity;
import com.MoA.moa_back.common.enums.UserRole;
import com.MoA.moa_back.common.vo.UserInterestVO;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserInfoResponseDto extends ResponseDto {
    private String userId;
    private String userNickname;
    private String userProfileImage;
    private String userIntroduce;
    private String userPhoneNumber;
    private String userEmail;
    private UserRole userRole;
    private UserInterestVO userInterestVO;
    
    private UserInfoResponseDto(UserEntity user, UserInterestVO interestVO) {
        this.userId = user.getUserId();
        this.userNickname = user.getUserNickname();
        this.userProfileImage = user.getProfileImage();
        this.userIntroduce = user.getUserIntroduce();
        this.userPhoneNumber = user.getUserPhoneNumber();
        this.userEmail = user .getUserEmail();
        this.userRole = user.getUserRole();
        this.userInterestVO = interestVO;
    }

    public static ResponseEntity<UserInfoResponseDto> success(UserEntity user, UserInterestVO interestVO){
        UserInfoResponseDto body = new UserInfoResponseDto(user, interestVO);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
