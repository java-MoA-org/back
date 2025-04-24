package com.MoA.moa_back.repository;

import com.MoA.moa_back.common.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// interface: 공지사항(NotificationEntity)에 대한 JPA Repository 인터페이스
// 기본적인 CRUD 기능 및 페이징, 정렬 기능을 제공
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
}