package com.MoA.moa_back.common.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.dto.request.usedtrade.PostUsedTradeRequestDto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name="usedTrade")
@Table(name="used_trade")
@Getter
@Setter
@NoArgsConstructor
public class UsedTradeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer tradeSequence;
  private String userId;
  private String itemTypeTag; // 물건 타입 ex) 전자기기, 옷
  private String usedItemStatusTag; // 물건 상태 ex) 사용감 있음 , 새상품, 사용감 없음
  private String creationDate;
  private String title;
  private String content;
  private String price;
  private String transactionStatus; // 거래상태 ex) 판매중, 판매완료
  private String location;
  private String detailLocation;
  @ElementCollection
  @CollectionTable(name="used_trade_images", joinColumns = @JoinColumn(name="trade_sequence"))
  @Column(name="image_url")
  private List<String> images = new ArrayList<>();

  @Column(nullable=false)
  private Integer views = 0;

  // 생성자에서 기본값으로 '판매중' 설정
  public UsedTradeEntity(PostUsedTradeRequestDto dto, String userId) {
    LocalDate now = LocalDate.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    this.userId = userId;
    this.creationDate = now.format(dateTimeFormatter);
    this.title = dto.getTitle();
    this.content = dto.getContent();
    this.itemTypeTag = dto.getItemTypeTag();
    this.usedItemStatusTag = dto.getUsedItemStatusTag();
    this.price = dto.getPrice();
    this.location = dto.getLocation();
    this.detailLocation = dto.getDetailLocation();
    this.images = dto.getImageList() != null ? dto.getImageList() : new ArrayList<>();
    this.transactionStatus = "판매중"; // 기본값 설정
  }

  // 판매완료로 상태를 변경하는 메서드
  public void markAsSold() {
    this.transactionStatus = "판매완료"; // 판매완료로 상태 변경
  }
}
