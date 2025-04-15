package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;

public interface MyPageService {

  // 유저 익명 게시글 불러오기
  ResponseEntity<? extends ResponseDto> getUserBoardList(String userNickname);

  
} 