package com.MoA.moa_back.common.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.BoardCommentEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class BoardCommentVO {

  private Integer commentSequence;
  private String commentWriterId;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime commentWriteDate;
  
  private String comment;

  private boolean isAuthor;
  private boolean isAnonymous;

  public BoardCommentVO(BoardCommentEntity boardCommentEntity, String postWriterId) {
    this.commentSequence = boardCommentEntity.getCommentSequence();
    this.commentWriterId = boardCommentEntity.getUserId();
    this.commentWriteDate = boardCommentEntity.getCreationDate();
    this.comment = boardCommentEntity.getBoardComment();
    this.isAuthor = boardCommentEntity.getUserId().equals(postWriterId);
    this.isAnonymous = !boardCommentEntity.getUserId().equals(postWriterId);
  }

  public static List<BoardCommentVO> getList(List<BoardCommentEntity> boardCommentEntities, String postWriterId) {
    List<BoardCommentVO> list = new ArrayList<>();
    int anonymousCount = 1;

    for (BoardCommentEntity boardCommentEntity : boardCommentEntities) {
      String displayName = boardCommentEntity.getUserId().equals(postWriterId) ? boardCommentEntity.getUserId() : "익명" + anonymousCount++;
      BoardCommentVO dto = new BoardCommentVO(boardCommentEntity, displayName);
      list.add(dto);
    }

    return list;
  }
}

