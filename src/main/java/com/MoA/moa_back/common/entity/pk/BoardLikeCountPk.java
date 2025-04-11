package com.MoA.moa_back.common.entity.pk;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeCountPk implements Serializable {

  private Integer boardSequence;
  private String userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BoardLikeCountPk that = (BoardLikeCountPk) o;

    if (!boardSequence.equals(that.boardSequence)) return false;
    return userId.equals(that.userId);
  }

  @Override
  public int hashCode() {
      return Objects.hash(boardSequence, userId);
  }

}
