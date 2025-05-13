package com.MoA.moa_back.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.usedtrade.PatchUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.request.usedtrade.PostUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.usedtrade.GetUsedTradeListResponseDto;
import com.MoA.moa_back.common.dto.response.usedtrade.GetUsedTradeResponseDto;

public interface UsedTradeService {

  // method: 중고거래 게시글 작성 //
  ResponseEntity<ResponseDto> postUsedTrade(PostUsedTradeRequestDto dto, String userId);
  // method: 게시판(태그) 별 게시글 목록 조회 (페이징)//
  ResponseEntity<? super GetUsedTradeListResponseDto> getUsedTradeListByTag(String tag, Integer pageNumber, String sortOption);
  // method: 중고거래글 상세 조회 및 조회수 증가 //
  ResponseEntity<? super GetUsedTradeResponseDto> getUsedTradeDetail(Integer tradeSequence);
  // method: 중고거래글 수정 //
  ResponseEntity<ResponseDto> patchUsedTrade(PatchUsedTradeRequestDto dto, Integer tradeSequence, String userId);
  // method: 중고거래글 삭제 //
  ResponseEntity<ResponseDto> deleteUsedTrade(Integer tradeSequence, String userId);
  // method: 중고거래글 (태그) 별 제목 검색 //
  ResponseEntity<? super GetUsedTradeListResponseDto> searchUsedTradeList(String tag, String keyword, Integer pageNumber);

  // method: 특정 중고거래글 좋아요 누르거나 취소 //
  ResponseEntity<ResponseDto> putUsedTradeLikeCount(Integer tradeSequence, String userId);

  // method: 거래 상태 변경 (판매중 -> 판매완료 / 판매완료 -> 판매중) //
  ResponseEntity<ResponseDto> patchTransactionStatus(Integer tradeSequence);
}
