package com.MoA.moa_back.common.vo.summary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.repository.UsedTradeLikeRepository;
import com.MoA.moa_back.repository.UserRepository;

import lombok.Getter;

@Getter
public class UsedTradeSummaryVO {
  private Integer tradeSequence;
  private String title;
  private Integer views;
  private int likeCount;
  private LocalDateTime creationDate;

    // 메인홈 전용 추가 필드
    private String userNickname;
    private String thumbnailImage;

  private UsedTradeSummaryVO(UsedTradeEntity usedTradeEntity, int likeCount){
    this.tradeSequence = usedTradeEntity.getTradeSequence();
    this.title = usedTradeEntity.getTitle();
    this.views = usedTradeEntity.getViews();
    this.likeCount = likeCount;
    this.creationDate = usedTradeEntity.getCreationDate();
  }

    // 메인 홈 전용 생성자
    private UsedTradeSummaryVO(UsedTradeEntity tradeEntity, int likeCount, String userNickname, String thumbnailImage) {
      this.tradeSequence = tradeEntity.getTradeSequence();
      this.title = tradeEntity.getTitle();
      this.views = tradeEntity.getViews();
      this.likeCount = likeCount;
      this.creationDate = tradeEntity.getCreationDate();
      this.userNickname = userNickname;
      this.thumbnailImage = thumbnailImage;
    }
  

  public static List<UsedTradeSummaryVO> getList(List<UsedTradeEntity> tradeEntities, UsedTradeLikeRepository likeRepository){
    List<UsedTradeSummaryVO> list = new ArrayList<>();

    for(UsedTradeEntity tradeEntity : tradeEntities) {
      int likeCount = likeRepository.countByTradeSequence(tradeEntity.getTradeSequence());
      list.add(new UsedTradeSummaryVO(tradeEntity, likeCount));
    }
    return list;
  }

  // 메인 홈 전용 리스트 생성
  public static List<UsedTradeSummaryVO> getList(
    List<UsedTradeEntity> tradeEntities,
    UsedTradeLikeRepository likeRepository,
    UserRepository userRepository
  ) {
    List<UsedTradeSummaryVO> list = new ArrayList<>();

    for (UsedTradeEntity tradeEntity : tradeEntities) {
      int likeCount = likeRepository.countByTradeSequence(tradeEntity.getTradeSequence());
      String nickname = userRepository.findById(tradeEntity.getUserId())
                          .map(UserEntity::getUserNickname)
                          .orElse("탈퇴한 사용자");
      String thumbnail = (tradeEntity.getImages() != null && !tradeEntity.getImages().isEmpty())
                          ? tradeEntity.getImages().get(0)
                          : null;

      list.add(new UsedTradeSummaryVO(tradeEntity, likeCount, nickname, thumbnail));
    }
    return list;
  }
}
