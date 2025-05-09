package com.MoA.moa_back.common.dto.response.usedtrade;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsedTradeSummaryResponseDto {
  private Integer tradeSequence;
  private Integer views;
  private Integer likeCount;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  
  private List<String> images;
  private String title;
  private String writerId;
  private String location;
  private String usedItemStatusTag;
  private String profileImage;
  private String userNickname;
  private Integer price;
  private String itemTypeTag;
  private String transactionStatus;
}
