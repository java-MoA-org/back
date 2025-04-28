package com.MoA.moa_back.common.dto.response.daily;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DailySummaryResponseDto {
  private Integer dailySequence;
  private String title;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  
  private String profileImage;
  private String userNickname;
  private Integer views;
  private Integer likeCount; 
  private Integer commentCount;
  private List<String> images = new ArrayList<>();
}
