package com.MoA.moa_back.common.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseCode;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.ResponseMessage;

import lombok.Getter;


@Getter
public class GetBoardLikeCountResponseDto extends ResponseDto {

    private String code;
    private String message;
    private Integer likeCount;

    // 생성자: 모든 필드 초기화 //
    public GetBoardLikeCountResponseDto(String code, String message, Integer likeCount) {
        this.code = code;
        this.message = message;
        this.likeCount = likeCount;
    }

    // method: 성공 응답 생성 //
    public static ResponseEntity<GetBoardLikeCountResponseDto> success(int likeCount) {
        GetBoardLikeCountResponseDto body =
            new GetBoardLikeCountResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, likeCount);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
