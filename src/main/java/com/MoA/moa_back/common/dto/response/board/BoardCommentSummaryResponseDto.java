package com.MoA.moa_back.common.dto.response.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardCommentSummaryResponseDto {
  private String userId;    
  private String creationDate;
  private String writerNickname;
}
