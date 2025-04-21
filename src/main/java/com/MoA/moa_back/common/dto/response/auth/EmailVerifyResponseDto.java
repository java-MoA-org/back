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
public class EmailVerifyResponseDto extends ResponseDto{
    private String token;

    public static ResponseEntity<EmailVerifyResponseDto> success(String token){
        EmailVerifyResponseDto body = new EmailVerifyResponseDto(token);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
