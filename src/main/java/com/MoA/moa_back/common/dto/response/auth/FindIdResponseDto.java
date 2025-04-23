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
public class FindIdResponseDto extends ResponseDto{
    private String userId;

    public static ResponseEntity<FindIdResponseDto> success(String userId){
        FindIdResponseDto body = new FindIdResponseDto(userId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}