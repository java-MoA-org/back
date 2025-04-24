package com.MoA.moa_back.common.dto.response.auth;

import com.MoA.moa_back.common.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRefreshResponseDto extends ResponseDto {
    private String accessToken;
    private Integer expiration;

    public static TokenRefreshResponseDto success(String token, Integer expiration) {
        return new TokenRefreshResponseDto(token, expiration);
    }
}
