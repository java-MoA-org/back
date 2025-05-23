package com.MoA.moa_back.common.dto.response.usedtrade;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.enums.ItemTypeTag;
import com.MoA.moa_back.common.enums.TransactionStatus;
import com.MoA.moa_back.common.enums.UsedItemStatusTag;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUsedTradeResponseDto extends ResponseDto {

  private Integer tradeSequence;
  private String title;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  
  private String profileImage;
  private String writerNickname;
  private Integer views;
  private ItemTypeTag itemTypeTag;
  private UsedItemStatusTag usedItemStatusTag;
  private Integer price;
  private String location;
  private String detailLocation;
  private TransactionStatus transactionStatus;
  private List<String> images;
  private Integer likeCount;
  private Boolean liked;
  

  public static GetUsedTradeResponseDto of(
    UsedTradeEntity usedTrade,
    UserEntity user,
    int likeCount,
    Boolean liked
  ) {
    return new GetUsedTradeResponseDto(
      usedTrade.getTradeSequence(),
      usedTrade.getTitle(),
      usedTrade.getContent(),
      usedTrade.getCreationDate(),
      user.getProfileImage(),
      user.getUserNickname(),
      usedTrade.getViews(),
      usedTrade.getItemTypeTag(),
      usedTrade.getUsedItemStatusTag(),
      usedTrade.getPrice(),
      usedTrade.getLocation(),
      usedTrade.getDetailLocation(),
      usedTrade.getTransactionStatus(),
      usedTrade.getImages(),
      likeCount,
      liked 
    );
  }

  public static ResponseEntity<GetUsedTradeResponseDto> success(
    UsedTradeEntity usedTrade,
    UserEntity user,
    int likeCount,
    Boolean liked
  ) {
    GetUsedTradeResponseDto body = GetUsedTradeResponseDto.of(usedTrade, user, likeCount, liked);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
  
}
