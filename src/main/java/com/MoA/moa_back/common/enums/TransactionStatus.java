package com.MoA.moa_back.common.enums;

// 중고거래 상태: 판매중, 판매완료
public enum TransactionStatus {
  ON_SALE("판매중"),
  SOLD_OUT("판매완료"),
  RESERVED("예약중");

  private final String korean;

  TransactionStatus(String korean) {
    this.korean = korean;
  }

  public String getKorean() {
    return korean;
  }
  
}
