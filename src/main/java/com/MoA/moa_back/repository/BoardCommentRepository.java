package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.BoardCommentEntity;

import jakarta.transaction.Transactional;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Integer> {
  
  // 특정 게시글에 달린 모든 댓글을 작성일 기준 내림차순 조회 //
	List<BoardCommentEntity> findByBoardSequenceOrderByCreationDateDesc(Integer boardSequence);

  // 특정 게시글에 달린 모든 댓글을 삭제 (게시글 삭제 시 연쇄적으로 삭제 처리) //
  @Transactional
  void deleteByBoardSequence(Integer boardSequence);

  // 특정 댓글 하나만 삭제 //
  @Transactional
  void deleteByCommentSequence(Integer commentSequence);

  // 특정 게시글에 댓글 개수 반환 //
  int countByBoardSequence(Integer boardSequence);

}
