package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.request.usedtrade.PostUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.service.UsedTradeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/used-trade")
@RequiredArgsConstructor
public class UsedTradeController {
  
  private final UsedTradeService usedTradeService;

  // API: 중고거래 게시글 작성 //
  @PostMapping({"", "/"})
  public ResponseEntity<ResponseDto> postDaily(
    @RequestBody @Valid PostUsedTradeRequestDto requestBody
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = usedTradeService.postUsedTrade(requestBody, userId);
    return response;
  }

}
