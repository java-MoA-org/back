package com.MoA.moa_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.BoardLikeEntity;
import com.MoA.moa_back.common.entity.pk.BoardLikeCountPk;

import jakarta.transaction.Transactional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, BoardLikeCountPk> {

  // 유저가 해당 게시글에 좋아요를 눌렀는지 확인 (중복 방지) //
  boolean existsByBoardSequenceAndUserId(Integer boardSequence, String userId);

  // 유저가 누른 좋아요 삭제 (취소 기능) //
  @Modifying
  @Transactional
  void deleteByBoardSequenceAndUserId(Integer boardSequence, String userId);

  // 게시글에 눌린 전체 좋아요 개수 반환 //
  int countByBoardSequence(Integer boardSequence);

  // 게시글 삭제 시 해당 게시글의 좋아요 전체 삭제 //
  @Transactional
  void deleteByBoardSequence(Integer boardSequence);

  // 게시글에 눌린 전체 좋아요 개수를 boardSequence 기준으로 조회//
  int countByBoardSequence(int boardSequence);
}
