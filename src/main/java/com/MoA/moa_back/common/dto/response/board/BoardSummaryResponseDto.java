package com.MoA.moa_back.common.dto.response.board;

import java.time.LocalDateTime;

import com.MoA.moa_back.common.enums.BoardTagType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardSummaryResponseDto {
  private Integer boardSequence;
  private String title;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  
  private BoardTagType tag;
  private Integer views;
  private Integer likeCount;
  private Integer commentCount;
}
