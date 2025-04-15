package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MoA.moa_back.common.entity.DailyCommentEntity;

import jakarta.transaction.Transactional;

public interface DailyCommentRepository extends JpaRepository<DailyCommentEntity, Integer> {

  // 특정 일상 게시글에 달린 모든 댓글을 작성일 기준 내림차순 조회 //
	List<DailyCommentEntity> findByDailySequenceOrderByCreationDateDesc(Integer dailySequence);

  // 특정 일상 게시글에 달린 모든 댓글을 삭제 (게시글 삭제 시 연쇄적으로 삭제 처리) //
  @Transactional
  void deleteByDailySequence(Integer dailySequence);

  // 특정 댓글 하나만 삭제 //
  @Transactional
  void deleteByCommentSequence(Integer commentSequence);

  // 특정 일상 게시글에 댓글 개수 반환 //
  int countByDailySequence(Integer dailySequence);

}
