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

        // 읽음 처리 전용 메시지일 경우
        if ("READ".equals(message.getType())) {
            messageService.markMessagesAsRead(message.getSenderId(), message.getReceiverId());

            // 수신자에게 읽음 표시 메시지 전송
            messagingTemplate.convertAndSend(
                "/topic/messages/" + message.getReceiverId(),
                message
            );
            return;
        }

        // timestamp 수동 설정
        message.setTimestamp(LocalDateTime.now());

        // DB 저장
        MessageEntity saved = messageService.saveMessage(message);

        // 수신자에게 실시간 메시지 전송
        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiverId(), saved);
    }
}