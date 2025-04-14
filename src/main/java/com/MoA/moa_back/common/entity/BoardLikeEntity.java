package com.MoA.moa_back.common.entity;

import com.MoA.moa_back.common.entity.pk.BoardLikeCountPk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="boardLike")
@Table(name="board_like")
@IdClass(BoardLikeCountPk.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeEntity {
  
  @Id
  private Integer boardSequence;
  @Id
  private String userId;

}