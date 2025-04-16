package com.MoA.moa_back.common.enums;

// 게시글 태그 타입 //
public enum BoardTagType {
  GAME("게임"),
  TRAVEL("여행"),
  WORKOUT("운동"),
  MUSIC("음악"),
  ECONOMY("경제"),
  FASHION("패션"),
  FOOD("음식"),
  FREE("자유");

  private final String displayName;

  BoardTagType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

}
