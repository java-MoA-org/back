package com.MoA.moa_back.controller;

import com.MoA.moa_back.common.entity.MessageEntity;
import com.MoA.moa_back.common.dto.request.message.HideMessageRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.message.GetMessageRoomListResponseDto;
import com.MoA.moa_back.service.MessageService;
import com.MoA.moa_back.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.MoA.moa_back.common.dto.request.message.MessageReadRequestDto;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;
    private final JwtProvider jwtProvider;

    // 메시지 전송
    @PostMapping
    public MessageEntity sendMessage(@RequestBody MessageEntity message) {
        return messageService.saveMessage(message);
    }

    // 메시지 조회 (userId와 상대방 partnerId 간 모든 메시지)
    @GetMapping("/{userId}/{partnerId}")
    public List<MessageEntity> getMessageHistory(@PathVariable String userId, @PathVariable String partnerId) {
        List<MessageEntity> messages = messageService.getMessagesBetween(userId, partnerId);
        messageService.markMessagesAsRead(userId, partnerId); // 읽음 처리
        return messages;
    }

    // 메시지 목록 (대화 상대방, 마지막 메시지, 읽지 않은 여부 등 요약)
    @GetMapping("/rooms")
    public List<GetMessageRoomListResponseDto> getMessageRooms(@RequestHeader("Authorization") String authorization) {
        System.out.println("[🔍 Authorization 헤더] " + authorization); // 디버깅용 로그
        String token = authorization.replace("Bearer ", "");
        System.out.println("[🔐 JWT 토큰] " + token); // 디버깅용 로그

        String userId = jwtProvider.validate(token);
        System.out.println("[✅ 검증된 userId] " + userId); // 디버깅용 로그

        List<GetMessageRoomListResponseDto> rooms = messageService.getMessageRooms(userId);
        System.out.println("[📦 조회된 채팅방 수] " + rooms.size()); // 디버깅용 로그

        return rooms;
    }

    // 채팅방 숨김 처리
    @DeleteMapping("/hide-room/{userId}/{partnerId}")
    public ResponseEntity<?> hideChatRoom(
            @PathVariable String userId,
            @PathVariable String partnerId,
            @RequestHeader("Authorization") String authorization) {
        try {
            String token = authorization.replace("Bearer ", "");
            String validatedUserId = jwtProvider.validate(token);

            if (!validatedUserId.equals(userId)) {
                return ResponseEntity.status(403).body("권한이 없습니다.");
            }

            messageService.hideChatRoom(userId, partnerId);
            return ResponseEntity.ok().body("채팅방이 숨김 처리되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("JWT 인증 실패 또는 서버 오류");
        }
    }

    // 새 메시지 개수 조회
    @GetMapping("/get-alert")
    public ResponseEntity<? super ResponseDto> getnewMessageCount(@AuthenticationPrincipal String userId) {
        // System.out.println("authentication : " + authentication);
        // String userId = (String) authentication.getPrincipal();
        System.out.println("userId : "+userId);
        ResponseEntity<? super ResponseDto> response = messageService.getNewMessageCount(userId);
        System.out.println("response : " + response);

        return response;
    }
    
    // 메시지 가리기
    @PostMapping("/hide")
    public ResponseEntity<ResponseDto> hideMessage(@RequestBody HideMessageRequestDto hideMessageRequestDto, @AuthenticationPrincipal String userId) {
        
        ResponseEntity<ResponseDto> response = messageService.hideMessage(hideMessageRequestDto.getMessageNumber(), userId);
        System.out.println(response.toString());
        
        return response;
    }
    

    @PostMapping("/read")
    public ResponseEntity<?> markMessagesAsRead(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MessageReadRequestDto requestDto) {
        try {
            String token = authorization.replace("Bearer ", "");
            String userId = jwtProvider.validate(token);

            if (!userId.equals(requestDto.getUserId())) {
                return ResponseEntity.status(403).body("권한이 없습니다.");
            }

            messageService.markMessagesAsRead(requestDto.getUserId(), requestDto.getPartnerId());
            return ResponseEntity.ok().body("읽음 처리 완료");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("JWT 인증 실패 또는 서버 오류");
        }
    }
}