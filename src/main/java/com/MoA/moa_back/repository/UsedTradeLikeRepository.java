package com.MoA.moa_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.MoA.moa_back.common.entity.UsedTradeLikeEntity;
import com.MoA.moa_back.common.entity.pk.UsedTradeLikeCountPk;

import jakarta.transaction.Transactional;

public interface UsedTradeLikeRepository extends JpaRepository<UsedTradeLikeEntity, UsedTradeLikeCountPk> {
  
  // 유저가 해당 게시글에 좋아요를 눌렀는지 확인 (중복 방지) //
  boolean existsByTradeSequenceAndUserId(Integer tradeSequence, String userId);

  // 유저가 누른 좋아요 삭제 (취소 기능) //
  @Modifying
  @Transactional
  void deleteByTradeSequenceAndUserId(Integer tradeSequence, String userId);

  // 게시글에 눌린 전체 좋아요 개수 반환 //
  int countByTradeSequence(Integer tradeSequence);

  // 게시글 삭제 시 해당 게시글의 좋아요 전체 삭제 //
  @Transactional
  void deleteByTradeSequence(Integer tradeSequence);

}
