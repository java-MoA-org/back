package com.MoA.moa_back.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.DailyCommentEntity;
import com.MoA.moa_back.common.entity.UserEntity;

import lombok.Getter;

@Getter
public class DailyCommentVO {
  private Integer commentSequence;
  private String commentWriterId;
  private String commentWriteDate;
  private String comment;
  private String profileImage;
  private String writerNickname;

  public DailyCommentVO(DailyCommentEntity dailyCommentEntity, UserEntity userEntity) {
    this.commentSequence = dailyCommentEntity.getCommentSequence();
    this.commentWriterId = dailyCommentEntity.getUserId();
    this.commentWriteDate = dailyCommentEntity.getCreationDate().toString();
    this.comment = dailyCommentEntity.getDailyComment();
    this.profileImage = userEntity.getProfileImage();
    this.writerNickname = userEntity.getUserNickname();
  }

  public static List<DailyCommentVO> getList(List<DailyCommentEntity> dailyCommentEntities, List<UserEntity> userEntities) {
    List<DailyCommentVO> list = new ArrayList<>();
    for (int i = 0; i < dailyCommentEntities.size(); i++) {
      DailyCommentEntity dailyCommentEntity = dailyCommentEntities.get(i);
      UserEntity userEntity = userEntities.get(i);
      DailyCommentVO vo = new DailyCommentVO(dailyCommentEntity, userEntity);
      list.add(vo);
    }
    return list;
  }
}
