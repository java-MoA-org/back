package com.MoA.moa_back.common.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.UserInterestsEntity;
import com.MoA.moa_back.common.vo.UserInfoVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetUserInfoResponseDto extends ResponseDto{
    private String userNickname;
    private String userPhoneNumber;
    private UserInterestsEntity userInterests;
    private String userProfileImage;
    private String userIntroduce;
    private String joinType;
    

    private GetUserInfoResponseDto(UserInfoVO userInfoVO) {
        this.userNickname = userInfoVO.getUserNickname();
        this.userInterests = userInfoVO.getUserInterests();
        this.userPhoneNumber = userInfoVO.getUserPhoneNumber();
        this.userIntroduce = userInfoVO.getUserIntroduce();
        this.userProfileImage = userInfoVO.getUserProfileImage();
        this.joinType = userInfoVO.getJoinType();
        
    }

    public static ResponseEntity<GetUserInfoResponseDto> success(UserInfoVO userInfoVO){
        GetUserInfoResponseDto body = new GetUserInfoResponseDto(userInfoVO);
        return ResponseEntity.status(HttpStatus.OK).body(body);

        
    }
}
