package com.MoA.moa_back.common.dto.response.usedtrade;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.enums.ItemTypeTag;
import com.MoA.moa_back.common.enums.TransactionStatus;
import com.MoA.moa_back.common.enums.UsedItemStatusTag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUsedTradeResponseDto extends ResponseDto {

  private Integer tradeSequence;
  private String title;
  private String content;
  private String creationDate;
  private String profileImage;
  private String writerNickname;
  private Integer views;
  private ItemTypeTag itemTypeTag;
  private UsedItemStatusTag usedItemStatusTag;
  private String price;
  private String location;
  private String detailLocation;
  private TransactionStatus transactionStatus;
  private List<String> images;
  private Integer likeCount;
  private Boolean hasChatRoom;

  public static GetUsedTradeResponseDto of(
    UsedTradeEntity entity,
    UserEntity user,
    int likeCount,
    Boolean hasChatRoom
  ) {
    return new GetUsedTradeResponseDto(
      entity.getTradeSequence(),
      entity.getTitle(),
      entity.getContent(),
      entity.getCreationDate(),
      user.getProfileImage(),
      user.getUserNickname(),
      entity.getViews(),
      entity.getItemTypeTag(),
      entity.getUsedItemStatusTag(),
      entity.getPrice(),
      entity.getLocation(),
      entity.getDetailLocation(),
      entity.getTransactionStatus(),
      entity.getImages(),
      likeCount,
      hasChatRoom
    );
  }

  public static ResponseEntity<GetUsedTradeResponseDto> success(
    UsedTradeEntity entity,
    UserEntity user,
    int likeCount,
    Boolean hasChatRoom
  ) {
    GetUsedTradeResponseDto body = GetUsedTradeResponseDto.of(entity, user, likeCount, hasChatRoom);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
  
}
