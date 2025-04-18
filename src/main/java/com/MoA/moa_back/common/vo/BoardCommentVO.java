package com.MoA.moa_back.common.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.BoardCommentEntity;

import lombok.Getter;

@Getter
public class BoardCommentVO {

  private Integer commentSequence;
  private String commentWriterId;
  private LocalDateTime commentWriteDate;
  private String comment;

  public BoardCommentVO(BoardCommentEntity boardCommentEntity) {
    this.commentSequence = boardCommentEntity.getCommentSequence();
    this.commentWriterId = boardCommentEntity.getUserId();
    this.commentWriteDate = boardCommentEntity.getCreationDate();
    this.comment = boardCommentEntity.getBoardComment();
  }

  public static List<BoardCommentVO> getList(List<BoardCommentEntity> boardCommentEntities) {
    List<BoardCommentVO> list = new ArrayList<>();

    for (BoardCommentEntity boardCommentEntity : boardCommentEntities) {
      BoardCommentVO dto = new BoardCommentVO(boardCommentEntity);
      list.add(dto);
    }

    return list;
  }
  
}
