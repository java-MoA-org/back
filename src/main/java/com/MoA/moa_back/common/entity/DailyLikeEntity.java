package com.MoA.moa_back.common.entity;

import com.MoA.moa_back.common.entity.pk.DailyLikeCountPk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="dailyLike")
@Table(name="daily_like")
@IdClass(DailyLikeCountPk.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyLikeEntity {
  
  @Id
  private Integer dailySequence;
  @Id
  private String userId;

}
