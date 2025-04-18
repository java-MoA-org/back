package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.request.PatchUserInfoRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.mypage.GetUserPageResponseDto;
import com.MoA.moa_back.service.UserPageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user-page")
@RequiredArgsConstructor
public class UserPageController {

  private final UserPageService myPageService;

  // 마이페이지 userBoard 불러오기
  @GetMapping("/boards/{nickname}")
  public ResponseEntity<? super GetUserPageResponseDto> getUserBoards(
    @PathVariable("nickname") String userNickname
  ){
    ResponseEntity<? super GetUserPageResponseDto> response = myPageService.getUserBoardList(userNickname);
    return response;
  }

  @PatchMapping("/{nickname}/revise")
  public ResponseEntity<ResponseDto> patchUser(
    @PathVariable("nickname") String userNickname,
    @RequestBody @Valid PatchUserInfoRequestDto requestBody
  ){
    ResponseEntity<ResponseDto> responses = myPageService.patchUserInfo(requestBody, userNickname);
    return responses;
  }
}
