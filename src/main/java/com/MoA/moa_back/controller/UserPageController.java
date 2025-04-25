package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.request.user.PatchPasswordUserPageRequestDto;
import com.MoA.moa_back.common.dto.request.user.PatchUserInfoRequestDto;
import com.MoA.moa_back.common.dto.request.user.PostPasswordVerifyRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.mypage.GetUserPageResponseDto;
import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;
import com.MoA.moa_back.service.UserPageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user-page")
@RequiredArgsConstructor
public class UserPageController {

  private final UserPageService userPageService;

  // 마이페이지 userBoard 불러오기
  @GetMapping("/boards/{nickname}")
  public ResponseEntity<? super GetUserPageResponseDto> getUserBoards(
    @PathVariable("nickname") String userNickname
  ){
    ResponseEntity<? super GetUserPageResponseDto> response = userPageService.getUserBoardList(userNickname);
    return response;
  }
  // 유저 정보 수정 페이지 보기
  @GetMapping("/revise")
  public ResponseEntity<? super GetUserInfoResponseDto> getUser(
    @AuthenticationPrincipal String userId
  ){
    System.out.println(userId);
    ResponseEntity<? super GetUserInfoResponseDto> responses = userPageService.getUserInfo(userId);
    return responses;
  }
  // 유저 정보 수정
  @PatchMapping("/revise")
  public ResponseEntity<ResponseDto> patchUser(
    @RequestBody @Valid PatchUserInfoRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ){
    ResponseEntity<ResponseDto> responses = userPageService.patchUserInfo(requestBody, userId);
    return responses;
  }

  // 유저 비밀번호 체크
  @PostMapping("/password/verify")
  public ResponseEntity<ResponseDto> postPasswordVerify(
    @RequestBody PostPasswordVerifyRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ){
    ResponseEntity<ResponseDto> response = userPageService.passwordVerify(requestBody, userId);
    return response;
  }

  // 유저 비밀번호 변경
  @PatchMapping("/password/change")
public ResponseEntity<ResponseDto> patchPasswordChange(
    @RequestBody PatchPasswordUserPageRequestDto requestBody,
    @AuthenticationPrincipal String userId
) {
  ResponseEntity<ResponseDto> response = userPageService.patchPasswordChange(requestBody, userId);
    return response;
}
  
}
