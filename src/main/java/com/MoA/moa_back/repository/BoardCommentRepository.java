package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.BoardCommentEntity;

import jakarta.transaction.Transactional;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Integer> {
  
	List<BoardCommentEntity> findByBoardSequenceOrderByCreationDateDesc(Integer boardSequence);

  @Transactional
  void deleteByBoardSequence(Integer boardSequence);
}
