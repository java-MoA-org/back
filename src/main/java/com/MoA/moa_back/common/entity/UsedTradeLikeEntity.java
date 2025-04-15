package com.MoA.moa_back.common.entity;

import com.MoA.moa_back.common.entity.pk.UsedTradeLikeCountPk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="tradeLike")
@Table(name="trade_like")
@IdClass(UsedTradeLikeCountPk.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsedTradeLikeEntity {
  
  @Id
  private Integer tradeSequence;
  @Id
  private String userId;

}
