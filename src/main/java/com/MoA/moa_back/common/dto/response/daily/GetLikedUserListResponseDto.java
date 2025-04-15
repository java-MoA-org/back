package com.MoA.moa_back.common.dto.response.daily;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseCode;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.ResponseMessage;

import lombok.Getter;

@Getter
public class GetLikedUserListResponseDto extends ResponseDto {

    private String code;
    private String message;
    private List<LikedUserDto> likedUserList;

    public GetLikedUserListResponseDto(String code, String message, List<LikedUserDto> likedUserList) {
        this.code = code;
        this.message = message;
        this.likedUserList = likedUserList;
    }

    public static ResponseEntity<GetLikedUserListResponseDto> success(List<LikedUserDto> likedUserList) {
        GetLikedUserListResponseDto body = new GetLikedUserListResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, likedUserList);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
