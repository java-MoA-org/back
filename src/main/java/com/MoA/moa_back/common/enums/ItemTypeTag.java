package com.MoA.moa_back.common.enums;

// 중고거래 상품 타입 태그 //
public enum ItemTypeTag {
  ELECTRONICS("전자기기"),
  CLOTHING("의류"),
  FURNITURE("가구"),
  BOOKS("도서"),
  BEAUTY("뷰티/미용"),
  SPORTS("운동/스포츠"),
  FOOD("식품"),
  ETC("기타");

  private final String displayName;

  ItemTypeTag(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

}
