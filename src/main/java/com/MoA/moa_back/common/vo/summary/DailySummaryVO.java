package com.MoA.moa_back.common.vo.summary;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.repository.DailyCommentRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;
import com.MoA.moa_back.repository.UserRepository;

import lombok.Getter;

@Getter
public class DailySummaryVO {
  private Integer dailySequence;
  private String title;
  private Integer views;
  private int likeCount;
  private String creationDate;

  // 메인홈용 추가 필드
  private String userNickname;
  private int commentCount;

  private DailySummaryVO(DailyEntity dailyEntity, int likeCount){
    this.dailySequence = dailyEntity.getDailySequence();
    this.title = dailyEntity.getTitle();
    this.views = dailyEntity.getViews();
    this.likeCount = likeCount;
    this.creationDate = dailyEntity.getCreationDate();
  }

    // 메인 홈 전용 생성자
    private DailySummaryVO(DailyEntity dailyEntity, int likeCount, int commentCount, String userNickname) {
      this.dailySequence = dailyEntity.getDailySequence();
      this.title = dailyEntity.getTitle();
      this.views = dailyEntity.getViews();
      this.likeCount = likeCount;
      this.creationDate = dailyEntity.getCreationDate();
      this.commentCount = commentCount;
      this.userNickname = userNickname;
    }

  public static List<DailySummaryVO> getList(List<DailyEntity> dailyEntities, DailyLikeRepository likeRepository){
    List<DailySummaryVO> list = new ArrayList<>();

    for(DailyEntity dailyEntity : dailyEntities){
      int likeCount = likeRepository.countByDailySequence(dailyEntity.getDailySequence());
      list.add(new DailySummaryVO(dailyEntity, likeCount));
    }
    return list;
  }
  
    // 메인 홈 전용 리스트 생성
  public static List<DailySummaryVO> getList(
    List<DailyEntity> dailyEntities,
    DailyLikeRepository likeRepository,
    DailyCommentRepository commentRepository,
    UserRepository userRepository
  ) {
    List<DailySummaryVO> list = new ArrayList<>();

    for (DailyEntity dailyEntity : dailyEntities) {
      int likeCount = likeRepository.countByDailySequence(dailyEntity.getDailySequence());
      int commentCount = commentRepository.countByDailySequence(dailyEntity.getDailySequence());
      String nickname = userRepository.findById(dailyEntity.getUserId())
                          .map(UserEntity::getUserNickname)
                          .orElse("탈퇴한 사용자");

      list.add(new DailySummaryVO(dailyEntity, likeCount, commentCount, nickname));
    }
    return list;
  }
}