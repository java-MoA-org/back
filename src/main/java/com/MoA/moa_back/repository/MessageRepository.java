package com.MoA.moa_back.repository;

import com.MoA.moa_back.common.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    // 두 유저 간의 메시지를 시간순으로 조회
    List<MessageEntity> findBySenderIdAndReceiverIdOrderByTimestampAsc(String senderId, String receiverId);

    // 양방향 메시지 전체 가져오기 (각 사용자에게 보이는 메시지만)
    @Query("SELECT m FROM MessageEntity m WHERE " +
           "((m.senderId = :senderId1 AND m.receiverId = :receiverId1 AND m.visibleToSender = true) OR " +
           "(m.senderId = :senderId2 AND m.receiverId = :receiverId2 AND m.visibleToReceiver = true)) " +
           "ORDER BY m.timestamp ASC")
    List<MessageEntity> findVisibleMessagesBetweenUsers(
        @Param("senderId1") String senderId1,
        @Param("receiverId1") String receiverId1,
        @Param("senderId2") String senderId2,
        @Param("receiverId2") String receiverId2
    );

    // 양방향 마지막 메시지 1개 가져오기
    @Query("SELECT m FROM MessageEntity m WHERE " +
           "(m.senderId = :userId1 AND m.receiverId = :userId2) OR " +
           "(m.senderId = :userId2 AND m.receiverId = :userId1) " +
           "ORDER BY m.timestamp DESC")
    List<MessageEntity> findLastMessageBetweenUsers(@Param("userId1") String userId1, @Param("userId2") String userId2);

    // 특정 유저의 모든 채팅 상대 ID 목록 (중복 제거)
    @Query("SELECT DISTINCT CASE WHEN m.senderId = :userId THEN m.receiverId ELSE m.senderId END " +
           "FROM MessageEntity m WHERE m.senderId = :userId OR m.receiverId = :userId")
    List<String> findAllPartnerIds(@Param("userId") String userId);

    // 읽지 않은 메시지 존재 여부 확인
    boolean existsByReceiverIdAndSenderIdAndIsReadFalse(String receiverId, String senderId);

    // 특정 유저가 받은 메시지를 읽음 처리
    @Modifying
    @Query("UPDATE MessageEntity m SET m.isRead = true WHERE m.receiverId = :receiverId AND m.senderId = :senderId AND m.isRead = false")
    void markMessagesAsRead(@Param("receiverId") String receiverId, @Param("senderId") String senderId);
    // 양방향 메시지를 각 사용자 기준으로 필터링하여 시간순 정렬
    List<MessageEntity> findBySenderIdAndReceiverIdAndVisibleToSenderTrueOrSenderIdAndReceiverIdAndVisibleToReceiverTrueOrderByTimestampAsc(
        String senderId1, String receiverId1, String senderId2, String receiverId2
    );


    Integer countByReceiverIdAndIsRead(String receiverId,boolean isRead);

}
