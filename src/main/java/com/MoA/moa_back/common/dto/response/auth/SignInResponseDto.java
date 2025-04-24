package com.MoA.moa_back.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.enums.UserRole;

import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto {

    private String accessToken;
    private Integer expiration;
    private String userRole;

    // 생성자
    private SignInResponseDto(String accessToken, UserRole userRole) {
        this.accessToken = accessToken;
        this.expiration = 30 * 60;
        if(userRole.equals("ADMIN")){this.expiration = 60 * 60 * 24;}
        this.userRole = userRole.toString(); // Enum을 문자열로 저장
    }

    // success 메서드
    public static ResponseEntity<SignInResponseDto> success(String accessToken, UserRole userRole) {
        SignInResponseDto body = new SignInResponseDto(accessToken, userRole );
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}