package com.MoA.moa_back.repository;

import com.MoA.moa_back.common.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    // 두 유저 간의 메시지를 시간순으로 조회
    List<MessageEntity> findBySenderIdAndReceiverIdOrderByTimestampAsc(String senderId, String receiverId);

    // 양방향 메시지 전체 가져오기
    List<MessageEntity> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestampAsc(
        String senderId1, String receiverId1, String senderId2, String receiverId2
    );

    // 마지막 메시지 1개 (최근 대화용)
    MessageEntity findTopBySenderIdAndReceiverIdOrderByTimestampDesc(String senderId, String receiverId);

    // 특정 유저의 모든 채팅 상대 ID 목록 (중복 제거)
    @Query("SELECT DISTINCT CASE WHEN m.senderId = :userId THEN m.receiverId ELSE m.senderId END " +
           "FROM MessageEntity m WHERE m.senderId = :userId OR m.receiverId = :userId")
    List<String> findAllPartnerIds(@Param("userId") String userId);

    // 읽지 않은 메시지 존재 여부 확인
    boolean existsByReceiverIdAndSenderIdAndIsReadFalse(String receiverId, String senderId);
}