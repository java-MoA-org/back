package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/my-page")
@RequiredArgsConstructor
public class UserPageController {

  private final MyPageService myPageService;

  // 마이페이지 userBoard 불러오기
  @GetMapping("/boards/{nickname}")
  public ResponseEntity<? extends ResponseDto> getUserBoards(
    @PathVariable("nickname") String userNickname
  ){
    ResponseEntity<? extends ResponseDto> response = myPageService.getUserBoardList(userNickname);
    return response;
  }
}
