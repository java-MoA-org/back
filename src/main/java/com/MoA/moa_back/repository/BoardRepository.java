package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.TagType;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
  
  // method: 특정 게시글 존재 여부 확인 //
  boolean existsByBoardSequence(Integer boardSequence);

  // method: 게시글 시퀀스로 조회 //
  BoardEntity findByBoardSequence(Integer boardSequence);
  
  // method: 특정 유저가 작성한 게시글들을 최신순으로 조회 //
  List<BoardEntity> findByUserIdOrderByCreationDateDesc(String userId);
  
  // method: 전체 게시글을 최신순으로 조회 //
  List<BoardEntity> findByOrderByBoardSequenceDesc();
  
  // method: 태그에 해당하는 게시글 목록을 페이징하여 조회 //
  Page<BoardEntity> findByTag(TagType tag, Pageable pageable);
  
}
