package com.MoA.moa_back.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="boardComment")
@Table(name="board_comment")
@Getter
@Setter
public class BoardCommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer commentSequence;

  private String userId;

  private Integer boardSequence;

  private String boardComment;
}
