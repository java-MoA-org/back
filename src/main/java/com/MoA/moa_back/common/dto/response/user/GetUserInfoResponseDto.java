package com.MoA.moa_back.common.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.UserEntity;
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
    private UserInfoVO userInfoVO;

    public static ResponseEntity<GetUserInfoResponseDto> success(UserInfoVO userInfoVO){
        GetUserInfoResponseDto body = new GetUserInfoResponseDto(userInfoVO);
        return ResponseEntity.status(HttpStatus.OK).body(body);

        
    }
}
