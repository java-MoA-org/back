package com.MoA.moa_back.service;

import com.MoA.moa_back.common.entity.MessageEntity;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.message.GetMessageRoomListResponseDto;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface MessageService {

    // 메시지를 저장하는 메서드
    MessageEntity saveMessage(MessageEntity message);

    // 두 사용자 간의 메시지 목록을 조회하는 메서드
    List<MessageEntity> getMessagesBetween(String userA, String userB);

    // 두 사용자 간의 가장 최근 메시지를 조회하는 메서드
    MessageEntity getLastMessage(String senderId, String receiverId);

    // 채팅방 목록(읽음 여부 포함)을 가져오는 메서드
    List<GetMessageRoomListResponseDto> getMessageRoomList(String userId);

    // 채팅방 목록(읽음 여부 무시)을 가져오는 메서드
    List<GetMessageRoomListResponseDto> getMessageRooms(String userId);

    // 특정 사용자로부터 받은 메시지를 읽음 처리하는 메서드
    void markMessagesAsRead(String receiverId, String senderId); 

    // 사용자가 특정 채팅방을 숨기기 위한 메서드 (소프트 삭제)
    void hideChatRoom(String userId, String partnerId);

    // 사용자 아이디 기반 내 메시지 개수 조회
    ResponseEntity<? super ResponseDto> getNewMessageCount(String userId);

    // 메시지 가리기
    ResponseEntity<ResponseDto> hideMessage(Integer messageNumber, String userId);
}
