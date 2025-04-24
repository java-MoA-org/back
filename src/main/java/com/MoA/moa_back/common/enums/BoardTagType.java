package com.MoA.moa_back.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    private final String koreanTag;

    BoardTagType(String koreanTag) {
        this.koreanTag = koreanTag;
    }

    @JsonCreator
    public static BoardTagType fromKoreanTag(String koreanTag) {
        for (BoardTagType type : values()) {
            if (type.koreanTag.equals(koreanTag)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown tag: " + koreanTag);
    }

    @JsonValue
    public String getKoreanTag() {
        return koreanTag;
    }
}
