package com.MoA.moa_back.service;

import com.MoA.moa_back.common.entity.MessageEntity;
import com.MoA.moa_back.common.dto.response.message.GetMessageRoomListResponseDto;
import java.util.List;

public interface MessageService {

    MessageEntity saveMessage(MessageEntity message);

    List<MessageEntity> getMessagesBetween(String userA, String userB);

    MessageEntity getLastMessage(String senderId, String receiverId);

    List<GetMessageRoomListResponseDto> getMessageRoomList(String userId);

    List<GetMessageRoomListResponseDto> getMessageRooms(String userId);
}