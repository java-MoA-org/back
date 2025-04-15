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
public class DailyLikeCountPk implements Serializable {
  
  private Integer dailySequence;
  
  private String userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DailyLikeCountPk that = (DailyLikeCountPk) o;

    if (!dailySequence.equals(that.dailySequence)) return false;
    return userId.equals(that.userId);
  }

  @Override
  public int hashCode() {
      return Objects.hash(dailySequence, userId);
  }

}
