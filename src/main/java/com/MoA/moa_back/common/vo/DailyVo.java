package com.MoA.moa_back.common.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.repository.DailyCommentRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class DailyVo {
  private Integer dailySequence;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  
  private String title;
  private int likeCount;
  private int commentCount;
  private String previewContent;
  private int views;

  private DailyVo(DailyEntity dailyEntity, int likeCount, int commentCount, int views) {
    this.dailySequence = dailyEntity.getDailySequence();
    this.creationDate = LocalDateTime.now();
    this.title = dailyEntity.getTitle();
    this.likeCount = likeCount;
    this.commentCount = commentCount;
    this.views = views;

    String content = dailyEntity.getContent();
    this.previewContent = content.length() > 50 ? content.substring(0, 50) + "..." : content;
  }

  public static List<DailyVo> getList(List<DailyEntity> dailyEntities, DailyLikeRepository likeRepository, DailyCommentRepository commentRepository) {
    List<DailyVo> list = new ArrayList<>();

    for (DailyEntity dailyEntity : dailyEntities) {
      int likeCount = likeRepository.countByDailySequence(dailyEntity.getDailySequence());
      int commentCount = commentRepository.countByDailySequence(dailyEntity.getDailySequence());
      int views = dailyEntity.getViews();
      list.add(new DailyVo(dailyEntity, likeCount, commentCount, views));
    }

    return list;
  }
}

