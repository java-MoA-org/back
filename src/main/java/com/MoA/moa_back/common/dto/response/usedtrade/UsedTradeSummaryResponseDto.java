package com.MoA.moa_back.common.dto.response.usedtrade;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsedTradeSummaryResponseDto {
  private Integer tradeSequence;
  private Integer views;
  private Integer likeCount;
  private String creationDate;
  private List<String> images;
  private String title;
  private String writerId;
  private String location;
  private String usedItemStatusTag;
  private String profileImage;
  private String userNickname;
}
