package com.MoA.moa_back.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

  @JsonValue
  public String getDisplayName() {
    return displayName;
  }

  @JsonCreator
  public static ItemTypeTag from(String value) {
    for (ItemTypeTag tag : values()) {
      if (tag.displayName.equals(value) || tag.name().equals(value)) {
        return tag;
      }
    }
    throw new IllegalArgumentException("Unknown itemTypeTag: " + value);
  }
}

