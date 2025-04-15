package com.MoA.moa_back.common.dto.response.daily;

import java.util.List;

import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetDailyDetailResponseDto {
  private Integer dailySequence;
  private String title;
  private String content;
  private String creationDate;
  private String profileImage;
  private String writerNickname;
  private Integer views;
  private Integer likeCount;
  private List<DailyCommentResponseDto> comments;

  public static GetDailyDetailResponseDto of(
    DailyEntity daily,
    int likeCount,
    List<DailyCommentResponseDto> commentList,
    UserEntity user
  ) {
    return new GetDailyDetailResponseDto(
      daily.getDailySequence(),
      daily.getTitle(),
      daily.getContent(),
      daily.getCreationDate(),
      user.getProfileImage(),
      user.getUserNickname(),
      daily.getViews(),
      likeCount,
      commentList
    );
  }
}
