package com.MoA.moa_back.common.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.dto.request.usedtrade.PatchUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.request.usedtrade.PostUsedTradeRequestDto;
import com.MoA.moa_back.common.enums.ItemTypeTag;
import com.MoA.moa_back.common.enums.TransactionStatus;
import com.MoA.moa_back.common.enums.UsedItemStatusTag;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "usedTrade")
@Table(name = "used_trade")
@Getter
@Setter
@NoArgsConstructor
public class UsedTradeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer tradeSequence;

  @Enumerated(EnumType.STRING)
  private ItemTypeTag itemTypeTag;

  @Enumerated(EnumType.STRING)
  private UsedItemStatusTag usedItemStatusTag;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;

  @Enumerated(EnumType.STRING)
  private TransactionStatus transactionStatus;

  @ElementCollection
  @CollectionTable(name="used_trade_images", joinColumns = @JoinColumn(name="trade_sequence"))
  @Column(name="image_url")
  private List<String> images = new ArrayList<>();

  @Column(nullable = false)
  private Integer views = 0;

  private String userId;
  private String title;
  private String content;
  private Integer price;
  private String location;
  private String detailLocation;

  public UsedTradeEntity(PostUsedTradeRequestDto dto, String userId) {
    this.userId = userId;
    this.creationDate = LocalDateTime.now();
    this.title = dto.getTitle();
    this.content = dto.getContent();
    this.itemTypeTag = dto.getItemTypeTag();
    this.usedItemStatusTag = dto.getUsedItemStatusTag();
    this.price = dto.getPrice();
    this.location = dto.getLocation();
    this.detailLocation = dto.getDetailLocation();
    this.images = dto.getImageList();
    this.transactionStatus = TransactionStatus.ON_SALE;
  }

  public void patch(PatchUsedTradeRequestDto dto) {
    this.title = dto.getTitle();
    this.content = dto.getContent();
    this.price = dto.getPrice();
    this.location = dto.getLocation();
    this.detailLocation = dto.getDetailLocation();
    this.images = dto.getImageList();
  }

}
