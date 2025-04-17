package com.MoA.moa_back.common.dto.response.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.BoardCommentEntity;

import lombok.Getter;

@Getter
public class BoardCommentResponseDto {

  private Integer commentSequence;
  private String commentWriterId;
  private LocalDateTime commentWriteDate;
  private String comment;

  public BoardCommentResponseDto(BoardCommentEntity boardCommentEntity) {
    this.commentSequence = boardCommentEntity.getCommentSequence();
    this.commentWriterId = boardCommentEntity.getUserId();
    this.commentWriteDate = boardCommentEntity.getCreationDate();
    this.comment = boardCommentEntity.getBoardComment();
  }

  public static List<BoardCommentResponseDto> getList(List<BoardCommentEntity> boardCommentEntities) {
    List<BoardCommentResponseDto> list = new ArrayList<>();

    for (BoardCommentEntity boardCommentEntity : boardCommentEntities) {
      BoardCommentResponseDto dto = new BoardCommentResponseDto(boardCommentEntity);
      list.add(dto);
    }

    return list;
  }
  
}
