package com.MoA.moa_back.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberVerifyResponseDto extends ResponseDto{
    private String token;
    private String verifyCode;

    public static ResponseEntity<PhoneNumberVerifyResponseDto> success(String token, String verifyCode){
        PhoneNumberVerifyResponseDto body = new PhoneNumberVerifyResponseDto(token, verifyCode);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    
}
