package com.MoA.moa_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.usedtrade.PostUsedTradeRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.repository.UsedTradeRepository;
import com.MoA.moa_back.service.UsedTradeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsedTradeServiceImplement implements UsedTradeService {

  private final UsedTradeRepository usedTradeRepository;
  
  // method: 중고거래 게시글 작성 //
  @Override
  public ResponseEntity<ResponseDto> postUsedTrade(PostUsedTradeRequestDto dto, String userId) {
    try {
      UsedTradeEntity usedTradeEntity = new UsedTradeEntity(dto, userId);
      usedTradeRepository.save(usedTradeEntity);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.CREATED);
  }
  
}
