package com.MoA.moa_back.common.entity;

import java.time.LocalDateTime;

import com.MoA.moa_back.common.dto.request.board.PostBoardCommentRequestDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="boardComment")
@Table(name="board_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer commentSequence;

  private String userId;
  private Integer boardSequence;

  private String boardComment;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;

  public BoardCommentEntity(PostBoardCommentRequestDto dto, Integer boardSequence, String userId) {
    this.userId = userId;
    this.boardSequence = boardSequence;
    this.creationDate = LocalDateTime.now();
    this.boardComment = dto.getBoardComment();
  }

}
