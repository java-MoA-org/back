package com.MoA.moa_back.service.implement;

import com.MoA.moa_back.common.entity.MessageEntity;
import com.MoA.moa_back.repository.MessageRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.MoA.moa_back.common.dto.response.message.GetMessageRoomListResponseDto;
import com.MoA.moa_back.common.entity.UserEntity; 
import java.util.ArrayList;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImplement implements MessageService {

    // repository: 메시지와 유저 관련 데이터 접근
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // service: 메시지 저장
    @Override
    public MessageEntity saveMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

    // service: 두 사용자 간 메시지 목록 조회 (소프트 삭제된 메시지는 제외)
    @Override
    public List<MessageEntity> getMessagesBetween(String userA, String userB) {
        return messageRepository.findBySenderIdAndReceiverIdAndVisibleToSenderTrueOrSenderIdAndReceiverIdAndVisibleToReceiverTrueOrderByTimestampAsc(
            userA, userB, userB, userA
        );
    }

    // service: 두 사용자 간 마지막 메시지 1건 조회
    @Override
    public MessageEntity getLastMessage(String senderId, String receiverId) {
        return messageRepository
            .findLastMessageBetweenUsers(senderId, receiverId)
            .stream().findFirst().orElse(null);
    }

    // service: 채팅방 요약 목록 조회 (닉네임, 프로필 포함)
    @Override
    public List<GetMessageRoomListResponseDto> getMessageRoomList(String userId) {
        List<String> partnerIds = messageRepository.findAllPartnerIds(userId); // repository: 상대방 ID 목록 조회
        List<GetMessageRoomListResponseDto> result = new ArrayList<>();

        // loop: 각 상대방에 대해 채팅방 요약 생성
        for (String partnerId : partnerIds) {
            MessageEntity lastMsg = messageRepository
                .findLastMessageBetweenUsers(userId, partnerId)
                .stream().findFirst().orElse(null);
            if (lastMsg == null) continue;

            // condition: 삭제된 메시지는 목록에서 제외
            boolean isSender = lastMsg.getSenderId().equals(userId);
            if ((isSender && !lastMsg.isVisibleToSender()) || (!isSender && !lastMsg.isVisibleToReceiver())) continue;

            String lastMessage = lastMsg.getContent();
            LocalDateTime timestamp = lastMsg.getTimestamp();
            boolean unread = !isSender &&
                messageRepository.existsByReceiverIdAndSenderIdAndIsReadFalse(userId, partnerId);

            UserEntity partner = userRepository.findByUserId(partnerId);
            String nickname = partner != null ? partner.getUserNickname() : "알 수 없음";
            String profileImage = partner != null ? partner.getProfileImage() : "";

            // build: 채팅방 응답 DTO 구성
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

    // service: 위 메서드와 유사 — 호환성용 중복 정의
    @Override
    public List<GetMessageRoomListResponseDto> getMessageRooms(String userId) {
        List<String> partnerIds = messageRepository.findAllPartnerIds(userId);
        List<GetMessageRoomListResponseDto> result = new ArrayList<>();

        for (String partnerId : partnerIds) {
            MessageEntity lastMsg = messageRepository
                .findLastMessageBetweenUsers(userId, partnerId)
                .stream().findFirst().orElse(null);
            if (lastMsg == null) continue;

            boolean isSender = lastMsg.getSenderId().equals(userId);
            if ((isSender && !lastMsg.isVisibleToSender()) || (!isSender && !lastMsg.isVisibleToReceiver())) continue;

            String lastMessage = lastMsg.getContent();
            LocalDateTime timestamp = lastMsg.getTimestamp();
            boolean unread = !isSender &&
                messageRepository.existsByReceiverIdAndSenderIdAndIsReadFalse(userId, partnerId);

            UserEntity partner = userRepository.findByUserId(partnerId);
            String nickname = partner != null ? partner.getUserNickname() : "알 수 없음";
            String profileImage = partner != null ? partner.getProfileImage() : "";

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

    // service: 메시지를 읽음 처리
    @Override
    @Transactional // 트랜잭션 필요
    public void markMessagesAsRead(String receiverId, String senderId) {
        messageRepository.markMessagesAsRead(receiverId, senderId);
    }

    // service: 특정 유저가 채팅방을 삭제(숨김)하도록 처리
    @Override
    @Transactional
    public void hideChatRoom(String userId, String partnerId) {
        // repository: 채팅방 메시지 조회 (소프트 삭제 포함)
        List<MessageEntity> messages = messageRepository.findBySenderIdAndReceiverIdAndVisibleToSenderTrueOrSenderIdAndReceiverIdAndVisibleToReceiverTrueOrderByTimestampAsc(
            userId, partnerId, partnerId, userId
        );

        // loop: 각 메시지에 대해 보이는 대상 설정 변경
        for (MessageEntity msg : messages) {
            if (msg.getSenderId().equals(userId)) {
                msg.setVisibleToSender(false); // 보낸 사람 기준 숨김
            } else if (msg.getReceiverId().equals(userId)) {
                msg.setVisibleToReceiver(false); // 받은 사람 기준 숨김
            }
            messageRepository.save(msg); // 변경 저장
        }
    }
}
