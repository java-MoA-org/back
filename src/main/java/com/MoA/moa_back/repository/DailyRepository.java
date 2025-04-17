package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  
  // method: 아이디에 따라 게시글 조회
  List<DailyEntity> findByUserId(String userId);

  // method: 제목에 키워드가 포함된 일상 게시글 목록 조회 (페이징 포함) //
  Page<DailyEntity> findByTitleContaining(String keyword, Pageable pageable);

  // method: 메인 홈 최신 일상 글 5개 조회
  List<DailyEntity> findTop5ByOrderByCreationDateDesc();

  
}
