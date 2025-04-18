package com.MoA.moa_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.BoardCommentEntity;
import com.MoA.moa_back.common.vo.BoardCommentVO;

import lombok.Getter;

@Getter
public class GetBoardCommentResponseDto extends ResponseDto {

  private List<BoardCommentVO> comments;

  private GetBoardCommentResponseDto(List<BoardCommentEntity> commentEntities) {
    this.comments = BoardCommentVO.getList(commentEntities);
  }

  public static ResponseEntity<GetBoardCommentResponseDto> success(List<BoardCommentEntity> commentEntities) {
    GetBoardCommentResponseDto body = new GetBoardCommentResponseDto(commentEntities);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
