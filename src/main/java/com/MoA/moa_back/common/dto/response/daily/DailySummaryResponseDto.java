package com.MoA.moa_back.common.dto.response.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailySummaryResponseDto {
  private Integer dailySequence;
  private String title;
  private String content;
  private String creationDate;
  private String profileImage;
  private Integer views;
  private Integer likeCount; 
}
