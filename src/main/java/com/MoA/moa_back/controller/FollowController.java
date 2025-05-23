package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.Follow.GetFollowInfoResponseDto;
import com.MoA.moa_back.common.dto.response.Follow.GetFollowResponseDto;
import com.MoA.moa_back.service.FollowService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("api/v1/follow")
@RequiredArgsConstructor
public class FollowController {

  private final FollowService followService;

  @PostMapping("/{nickname}")
  public ResponseEntity<ResponseDto> postFollow(

    @AuthenticationPrincipal String userId,
    @PathVariable("nickname") String followeeNickname
  ){
    ResponseEntity<ResponseDto> response = followService.postFollow(userId, followeeNickname);
    return response;
  }

  @GetMapping("/number/{nickname}")
  public ResponseEntity<? super GetFollowResponseDto> getFollow(
    @AuthenticationPrincipal String userId,
    @PathVariable("nickname") String UserPageNickname
  ){
    ResponseEntity<? super GetFollowResponseDto> response = followService.getFollow(UserPageNickname);
    return response;
  }

  @GetMapping("/number/info/{nickname}")
  public ResponseEntity<? super GetFollowInfoResponseDto> getFollowInfo(
    @AuthenticationPrincipal String userId,
    @PathVariable("nickname") String UserPageNickname
  ){
    ResponseEntity<? super GetFollowInfoResponseDto> response = followService.getFollowInfo(userId, UserPageNickname);
    return response;
  }
}
