package com.MoA.moa_back.common.vo.summary;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.repository.UsedTradeLikeRepository;

import lombok.Getter;

@Getter
public class UsedTradeSummaryVO {
  private Integer boardSequence;
  private String title;
  private Integer views;
  private int likeCount;
  private String creationDate;

  private UsedTradeSummaryVO(UsedTradeEntity usedTradeEntity, int likeCount){
    this.boardSequence = usedTradeEntity.getTradeSequence();
    this.title = usedTradeEntity.getTitle();
    this.views = usedTradeEntity.getViews();
    this.likeCount = likeCount;
    this.creationDate = usedTradeEntity.getCreationDate();
  }

  public static List<UsedTradeSummaryVO> getList(List<UsedTradeEntity> tradeEntities, UsedTradeLikeRepository likeRepository){
    List<UsedTradeSummaryVO> list = new ArrayList<>();

    for(UsedTradeEntity tradeEntity : tradeEntities) {
      int likeCount = likeRepository.countByTradeSequence(tradeEntity.getTradeSequence());
      list.add(new UsedTradeSummaryVO(tradeEntity, likeCount));
    }
    return list;
  }
}
