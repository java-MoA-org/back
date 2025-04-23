package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.PatchUserInfoRequestDto;
import com.MoA.moa_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.mypage.GetUserPageResponseDto;
import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;


public interface UserPageService {

  // 유저 익명,일상, 중고거래 게시글 불러오기
  ResponseEntity<? super GetUserPageResponseDto> getUserBoardList(String userNickname);
  
  // 회원정보 수정 화면
  ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(String userId);

  // 회원정보 수정 
  ResponseEntity<ResponseDto> patchUserInfo(PatchUserInfoRequestDto dto, String userId);
  ResponseEntity<ResponseDto> nicknameCheck(NicknameCheckRequestDto dto);
} 