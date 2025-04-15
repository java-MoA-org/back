package com.MoA.moa_back.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.repository.BoardCommentRepository;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.MoA.moa_back.repository.DailyCommentRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;

import lombok.Getter;

@Getter
public class DailyVo {
  private Integer dailySequence;
  private String creationDate;
  private String title;
  private int likeCount;
  private int commentCount;
  private String previewContent;

  private DailyVo(DailyEntity dailyEntity, int likeCount, int commentCount) {
    this.dailySequence = dailyEntity.getDailySequence();
    this.creationDate = dailyEntity.getCreationDate();
    this.title = dailyEntity.getTitle();
    this.likeCount = likeCount;
    this.commentCount = commentCount;

    String content = dailyEntity.getContent();
    this.previewContent = content.length() > 50 ? content.substring(0, 50) + "..." : content;
  }

  public static List<DailyVo> getList(List<DailyEntity> dailyEntities, DailyLikeRepository likeRepository, DailyCommentRepository commentRepository) {
    List<DailyVo> list = new ArrayList<>();

    for (DailyEntity dailyEntity : dailyEntities) {
      int likeCount = likeRepository.countByDailySequence(dailyEntity.getDailySequence());
      int commentCount = commentRepository.countByDailySequence(dailyEntity.getDailySequence());
      list.add(new DailyVo(dailyEntity, likeCount, commentCount));
    }

    return list;
  }
}

