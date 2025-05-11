package com.MoA.moa_back.common.dto.response.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMessageRoomListResponseDto {
    private String partnerId;       // 상대방 ID
    private String nickname;        // 상대방 닉네임
    private String profileImage;    // 상대방 프로필 이미지 URL
    private String lastMessage;     // 마지막 메시지 내용 -> 리스트에 사용 
    private LocalDateTime timestamp; // 마지막 메시지 시간
    private boolean unread;         // 읽지 않은 메시지 여부
}
