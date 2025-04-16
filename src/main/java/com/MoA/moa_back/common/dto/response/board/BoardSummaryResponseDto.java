package com.MoA.moa_back.common.dto.response.board;

import com.MoA.moa_back.common.enums.BoardTagType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardSummaryResponseDto {
  private Integer boardSequence;
  private String title;
  private String content;
  private String creationDate;
  private BoardTagType tag;
  private Integer views;
  private Integer likeCount; 
}
