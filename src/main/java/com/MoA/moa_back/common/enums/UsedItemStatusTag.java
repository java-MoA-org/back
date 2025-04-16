package com.MoA.moa_back.common.enums;

// 중고거래 상품 상태 태그 //
public enum UsedItemStatusTag {
  NEW("새상품"),
  LIKE_NEW("사용감 거의 없음"),
  USED("사용감 있음"),
  DAMAGED("파손/고장 있음");

  private final String displayName;

  UsedItemStatusTag(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

}

