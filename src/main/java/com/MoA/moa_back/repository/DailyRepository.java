package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.DailyEntity;

@Repository
public interface DailyRepository extends JpaRepository<DailyEntity, Integer> {

  // method: 특정 게시글 존재 여부 확인 //
  boolean existsByDailySequence(Integer dailySequence);

  // method: 게시글 시퀀스로 조회 //
  DailyEntity findByDailySequence(Integer dailySequence);

  // method: 특정 유저가 작성한 게시글들을 작성일 기준 최신순으로 조회 //
  List<DailyEntity> findByUserIdOrderByCreationDateDesc(String userId);

  // method: 특정 유저가 작성한 게시글들을 시퀀스 기준 최신순으로 조회 //
  List<DailyEntity> findByUserIdOrderByDailySequenceDesc(String userId);

  // method: 전체 게시글을 시퀀스 기준 최신순으로 조회 //
  List<DailyEntity> findByOrderByDailySequenceDesc();
}
