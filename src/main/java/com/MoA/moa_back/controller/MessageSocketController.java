package com.MoA.moa_back.controller;

import com.MoA.moa_back.common.entity.MessageEntity;
import com.MoA.moa_back.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MessageSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat.send") // /app/chat.send 로 클라이언트에서 publish
    public void sendMessage(MessageEntity message) {
        message.setId(null); // ID 초기화로 Hibernate가 INSERT 수행하도록 유도
        

        System.out.println("[🟢 WebSocket 메시지 수신] " + message);

        // 읽음 처리 전용 메시지일 경우
        if ("READ".equals(message.getType())) {
            messageService.markMessagesAsRead(message.getSenderId(), message.getReceiverId());

            System.out.println("[👁️ 읽음 메시지 전송 대상] /topic/messages/" + message.getReceiverId());

            // 수신자에게 읽음 표시 메시지 전송
            messagingTemplate.convertAndSend(
                "/topic/messages/" + message.getReceiverId(),
                message
            );
            return;
        }

        // timestamp 수동 설정
        message.setTimestamp(LocalDateTime.now());

        System.out.println("[💾 저장 전 메시지] " + message);

        // DB 저장
        MessageEntity saved = messageService.saveMessage(message);

        System.out.println("[✅ 저장된 메시지] " + saved);

        // 수신자에게 실시간 메시지 전송

        System.out.println("[📤 전송 대상 경로] /topic/messages/" + saved.getReceiverId());

        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiverId(), saved);
    }
}