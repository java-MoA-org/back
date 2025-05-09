package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.Follow.GetFollowInfoResponseDto;
import com.MoA.moa_back.common.dto.response.Follow.GetFollowResponseDto;

public interface FollowService {
  ResponseEntity<ResponseDto> postFollow(String FolloweruserId, String followeeNickname);
  ResponseEntity<? super GetFollowResponseDto> getFollow(String userPageNickname);

  // 팔로우 정보 리스트 불러오기
  ResponseEntity<? super GetFollowInfoResponseDto> getFollowInfo(String loginUserId ,String userPageNickname);
}
