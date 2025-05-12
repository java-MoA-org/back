package com.MoA.moa_back.service.implement;

import com.MoA.moa_back.common.entity.MessageEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.repository.MessageRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.MoA.moa_back.common.dto.response.message.GetMessageRoomListResponseDto;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImplement implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public MessageEntity saveMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

    @Override
    public List<MessageEntity> getMessagesBetween(String userA, String userB) {
        return messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestampAsc(
            userA, userB, userB, userA
        );
    }

    @Override
    public MessageEntity getLastMessage(String senderId, String receiverId) {
        return messageRepository.findTopBySenderIdAndReceiverIdOrderByTimestampDesc(senderId, receiverId);
    }

    @Override
    public List<GetMessageRoomListResponseDto> getMessageRoomList(String userId) {
        List<String> partnerIds = messageRepository.findAllPartnerIds(userId);
        List<GetMessageRoomListResponseDto> result = new ArrayList<>();

        for (String partnerId : partnerIds) {
            MessageEntity lastMsg = messageRepository.findTopBySenderIdAndReceiverIdOrderByTimestampDesc(userId, partnerId);
            if (lastMsg == null) continue;

            String lastMessage = lastMsg.getContent();
            LocalDateTime timestamp = lastMsg.getTimestamp();
            boolean unread = messageRepository.existsByReceiverIdAndSenderIdAndIsReadFalse(userId, partnerId);

            // 추후 UserEntity 정보 연동 필요
            String nickname = "닉네임"; // placeholder
            String profileImage = ""; // placeholder

            result.add(GetMessageRoomListResponseDto.builder()
                .partnerId(partnerId)
                .nickname(nickname)
                .profileImage(profileImage)
                .lastMessage(lastMessage)
                .timestamp(timestamp)
                .unread(unread)
                .build());
        }

        return result;
    }

    @Override
    public List<GetMessageRoomListResponseDto> getMessageRooms(String userId) {
        List<String> partnerIds = messageRepository.findAllPartnerIds(userId);
        List<GetMessageRoomListResponseDto> result = new ArrayList<>();

        for (String partnerId : partnerIds) {
            MessageEntity lastMsg = messageRepository.findTopBySenderIdAndReceiverIdOrderByTimestampDesc(userId, partnerId);
            if (lastMsg == null) continue;

            String lastMessage = lastMsg.getContent();
            LocalDateTime timestamp = lastMsg.getTimestamp();
            boolean unread = messageRepository.existsByReceiverIdAndSenderIdAndIsReadFalse(userId, partnerId);

            UserEntity partnerUser = userRepository.findByUserId(partnerId);
            String nickname = partnerUser.getUserNickname();
            String profileImage = partnerUser.getProfileImage();

            result.add(GetMessageRoomListResponseDto.builder()
                .partnerId(partnerId)
                .nickname(nickname)
                .profileImage(profileImage)
                .lastMessage(lastMessage)
                .timestamp(timestamp)
                .unread(unread)
                .build());
        }

        return result;
    }
}