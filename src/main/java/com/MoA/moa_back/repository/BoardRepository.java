package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.enums.BoardTagType;

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
  Page<BoardEntity> findByTag(BoardTagType tag, Pageable pageable);

  // method: 아이디에 따라 게시글 조회
  List<BoardEntity> findByUserId(String userId);

  // method: 제목에 키워드가 포함된 게시글 목록 조회 (페이징 포함) //
  Page<BoardEntity> findByTitleContaining(String keyword, Pageable pageable);

  // method: 특정 태그 + 제목에 키워드가 포함된 게시글 목록 조회 (페이징 포함) //
  Page<BoardEntity> findByTagAndTitleContaining(BoardTagType tag, String keyword, Pageable pageable);

  // method: 메인 홈 최신 게시판 글 5개 조회
  List<BoardEntity> findTop5ByOrderByCreationDateDesc();
}