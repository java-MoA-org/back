package com.MoA.moa_back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.MoA.moa_back.common.dto.request.usedtrade.PostUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.request.usedtrade.PatchUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.usedtrade.GetUsedTradeListResponseDto;
import com.MoA.moa_back.common.dto.response.usedtrade.GetUsedTradeResponseDto;
import com.MoA.moa_back.service.UsedTradeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/used-trade")
@RequiredArgsConstructor
public class UsedTradeController {

  private final UsedTradeService usedTradeService;

  // API: 중고거래 게시글 작성 //
  @PostMapping(path = {"", "/"})
  public ResponseEntity<ResponseDto> postUsedTrade(
    @RequestBody @Valid PostUsedTradeRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> respons = usedTradeService.postUsedTrade(requestBody, userId);
    return respons;
  }

  // API: 중고거래 게시글 목록 조회 (전체 or 태그별) //
  @GetMapping("/{tag}/{pageNumber}")
  public ResponseEntity<? super GetUsedTradeListResponseDto> getUsedTradeList(
    @PathVariable("tag") String tag,
    @PathVariable("pageNumber") Integer pageNumber,
    @RequestParam(name = "sortOption", defaultValue = "LATEST") String sortOption
  ) {
    ResponseEntity<? super GetUsedTradeListResponseDto> respons = usedTradeService.getUsedTradeListByTag(tag, pageNumber, sortOption);
    return respons;
  }

  // API: 중고거래 게시글 상세 조회 (조회수 증가 포함) //
  @GetMapping("/{tradeSequence}")
  public ResponseEntity<? super GetUsedTradeResponseDto> getUsedTradeDetail(
    @PathVariable("tradeSequence") Integer tradeSequence,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetUsedTradeResponseDto> respons = usedTradeService.getUsedTradeDetail(tradeSequence, userId);
    return respons;
  }

  // API: 중고거래 게시글 수정 //
  @PatchMapping("/{tradeSequence}")
  public ResponseEntity<ResponseDto> patchUsedTrade(
    @RequestBody @Valid PatchUsedTradeRequestDto requestBody,
    @PathVariable("tradeSequence") Integer tradeSequence,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> respons = usedTradeService.patchUsedTrade(requestBody, tradeSequence, userId);
    return respons;
  }

  // API: 중고거래 게시글 삭제 //
  @DeleteMapping("/{tradeSequence}")
  public ResponseEntity<ResponseDto> deleteUsedTrade(
    @PathVariable("tradeSequence") Integer tradeSequence,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = usedTradeService.deleteUsedTrade(tradeSequence, userId);
    return response;
  }

  // API: 중고거래글 검색 (태그별 가능) //
  @GetMapping("/search")
  public ResponseEntity<? super GetUsedTradeListResponseDto> searchUsedTradeList(
    @RequestParam(value = "tag", defaultValue = "ALL") String tag,
    @RequestParam(value = "keyword", defaultValue = "") String keyword,
    @RequestParam(value = "page", defaultValue = "1") Integer pageNumber
  ) {
    ResponseEntity<? super GetUsedTradeListResponseDto> response = usedTradeService.searchUsedTradeList(tag, keyword, pageNumber);
    return response;
  }

  // API: 중고거래 게시글 찜하기 or 취소하기 //
  @PutMapping("/{tradeSequence}/likes")
  public ResponseEntity<ResponseDto> toggleUsedTradeLike(
    @PathVariable("tradeSequence") Integer tradeSequence,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = usedTradeService.putUsedTradeLikeCount(tradeSequence, userId);
    return response;
  }

  // API: 중고거래 거래상태 변경 //
  @PatchMapping("/{tradeSequence}/status")
  public ResponseEntity<ResponseDto> patchTransactionStatus(
    @PathVariable("tradeSequence") Integer tradeSequence,
    @RequestParam("updateStatus") String updateStatus,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = usedTradeService.patchTransactionStatus(tradeSequence, updateStatus);
    return response;
  }

}
