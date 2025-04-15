package com.MoA.moa_back.common.dto.response.daily;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.DailyCommentEntity;
import com.MoA.moa_back.common.entity.UserEntity;

import lombok.Getter;

@Getter
public class DailyCommentResponseDto {

  private Integer commentSequence;
  private String commentWriterId;
  private String commentWriteDate;
  private String comment;
  private String profileImage;
  private String writerNickname;

  private DailyCommentResponseDto(DailyCommentEntity dailyCommentEntity, UserEntity userEntity) {
    this.commentSequence = dailyCommentEntity.getCommentSequence();
    this.commentWriterId = dailyCommentEntity.getUserId();
    this.commentWriteDate = dailyCommentEntity.getCreationDate();
    this.comment = dailyCommentEntity.getDailyComment();
    this.profileImage = userEntity.getProfileImage();
    this.writerNickname = userEntity.getUserNickname();
  }

}