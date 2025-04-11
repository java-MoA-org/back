package com.MoA.moa_back.common.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto{
    
    
    private String accessToken;
    private Integer expiration;

    private SignInResponseDto(String accessToken){
        this.accessToken = accessToken;
        this.expiration = 60 * 30;
    }

    public static ResponseEntity<SignInResponseDto> success(String accessToken){
        SignInResponseDto body = new SignInResponseDto(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
