package com.MoA.moa_back.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "receiver_id", nullable = false)
    private String receiverId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "type")
    private String type; // "TEXT" or "IMAGE"

    @CreationTimestamp
    private LocalDateTime timestamp;

    @Column(name = "is_read", nullable = false)
    @JsonProperty("isRead")
    private boolean isRead;

    // 소프트 DELETE 연관 :해당 메시지를 보낸 사용자에게 보여질지 여부
    @Column(name = "visible_to_sender", nullable = false)
    private boolean visibleToSender = true;

    // 소프트 DELETE 연관 :해당 메시지를 받은 사용자에게 보여질지 여부
    @Column(name = "visible_to_receiver", nullable = false)
    private boolean visibleToReceiver = true;
}