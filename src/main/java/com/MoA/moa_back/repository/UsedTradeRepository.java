package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.enums.ItemTypeTag;

@Repository
public interface UsedTradeRepository extends JpaRepository<UsedTradeEntity, Integer>{

  // method: 특정 중고거래 게시글 존재 여부 확인 //
  boolean existsByTradeSequence(Integer tradeSequence);

  // method: 게시글 시퀀스로 조회 //
  UsedTradeEntity findByTradeSequence(Integer tradeSequence);

  // method: 특정 유저가 작성한 중고거래 게시글들을 작성일 기준 최신순으로 조회 //
  List<UsedTradeEntity> findByUserIdOrderByCreationDateDesc(String userId);

  // method: 특정 유저가 작성한 중고거래 게시글들을 시퀀스 기준 최신순으로 조회 //
  List<UsedTradeEntity> findByUserIdOrderByTradeSequenceDesc(String userId);

  // method: 전체 중고거래 게시글을 시퀀스 기준 최신순으로 조회 //
  List<UsedTradeEntity> findByOrderByTradeSequenceDesc();
  
  // method: 아이디에 따라 게시글 조회
  List<UsedTradeEntity> findByUserId(String userId);
  
  // method: 태그에 해당하는 게시글 목록을 페이징하여 조회 //
  Page<UsedTradeEntity> findByItemTypeTag(ItemTypeTag itemTypeTag, Pageable pageable);

  // method: 제목 키워드로만 검색 //
  Page<UsedTradeEntity> findByTitleContaining(String keyword, Pageable pageable);

  // method: 태그 + 제목 키워드로 검색 //
  Page<UsedTradeEntity> findByItemTypeTagAndTitleContaining(ItemTypeTag itemTypeTag, String keyword, Pageable pageable);

  // method: 메인 홈 최신 중고거래 글 5개 조회
  List<UsedTradeEntity> findTop5ByOrderByCreationDateDesc();  

}
