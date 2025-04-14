package com.MoA.moa_back.common.dto.response.board;

import com.MoA.moa_back.common.entity.TagType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardSummaryResponseDto {
  private Integer boardSequence;
  private String title;
  private String creationDate;
  private TagType tag;
  private Integer views;
  private Integer likeCount; 
}
