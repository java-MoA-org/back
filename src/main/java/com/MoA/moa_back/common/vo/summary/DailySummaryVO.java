package com.MoA.moa_back.common.vo.summary;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.repository.DailyLikeRepository;

import lombok.Getter;

@Getter
public class DailySummaryVO {
  private Integer boardSequence;
  private String title;
  private Integer views;
  private int likeCount;
  private String creationDate;

  private DailySummaryVO(DailyEntity dailyEntity, int likeCount){
    this.boardSequence = dailyEntity.getDailySequence();
    this.title = dailyEntity.getTitle();
    this.views = dailyEntity.getViews();
    this.likeCount = likeCount;
    this.creationDate = dailyEntity.getCreationDate();
  }
  public static List<DailySummaryVO> getList(List<DailyEntity> dailyEntities, DailyLikeRepository likeRepository){
    List<DailySummaryVO> list = new ArrayList<>();

    for(DailyEntity dailyEntity : dailyEntities){
      int likeCount = likeRepository.countByDailySequence(dailyEntity.getDailySequence());
      list.add(new DailySummaryVO(dailyEntity, likeCount));
    }
    return list;
  }
}
