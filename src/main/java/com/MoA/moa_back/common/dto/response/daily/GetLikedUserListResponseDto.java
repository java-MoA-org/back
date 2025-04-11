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

    // constructor: 전체 필드를 초기화하는 생성자 //
    public GetLikedUserListResponseDto(String code, String message, List<LikedUserDto> likedUserList) {
        this.code = code;
        this.message = message;
        this.likedUserList = likedUserList;
    }

    // method: 좋아요 유저 리스트 응답 성공 시 사용하는 정적 메서드 //
    public static ResponseEntity<GetLikedUserListResponseDto> success(List<LikedUserDto> likedUserList) {
        GetLikedUserListResponseDto body = new GetLikedUserListResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, likedUserList);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
