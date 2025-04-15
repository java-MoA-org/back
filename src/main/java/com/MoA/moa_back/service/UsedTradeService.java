package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.usedtrade.PostUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;

public interface UsedTradeService {

  // method: 중고거래 게시글 작성 //
  ResponseEntity<ResponseDto> postUsedTrade(PostUsedTradeRequestDto dto, String userId);

}
