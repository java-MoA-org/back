package com.MoA.moa_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.MoA.moa_back.common.entity.BoardLikeEntity;
import com.MoA.moa_back.common.entity.pk.BoardLikeCountPk;

public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, BoardLikeCountPk> {

  // method : 해당 유저가 해당 게시글에 좋아요를 눌렀는지 확인 //
  boolean existsByBoardSequenceAndUserId(Integer boardSequence, String userId);

  // method : 해당 유저가 해당 게시글에 눌렀던 좋아요를 삭제 //
  void deleteByBoardSequenceAndUserId(Integer boardSequence, String userId);

  // method : 특정 게시글에 눌린 좋아요의 총 개수를 반환 //
  int countByBoardSequence(Integer boardSequence);
}
