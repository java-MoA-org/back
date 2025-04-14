package com.MoA.moa_back.common.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.MoA.moa_back.common.dto.request.board.PostBoardCommentRequestDto;

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
  private String creationDate;

  public BoardCommentEntity(PostBoardCommentRequestDto dto, Integer boardSequence, String userId) {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    this.userId = userId;
    this.boardSequence = boardSequence;
    this.creationDate = now.format(dateTimeFormatter);
    this.boardComment = dto.getBoardComment();
  }

}
