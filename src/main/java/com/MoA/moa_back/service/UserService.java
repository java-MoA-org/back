package com.MoA.moa_back.service;

import com.MoA.moa_back.common.dto.response.user.SearchUserResponseDto;
import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    // 키워드 기반 유저 검색
    SearchUserResponseDto searchUser(String keyword);

    // userId 기반 유저 전체 정보 조회
    ResponseEntity<? super GetUserInfoResponseDto> getUserInfoById(String userId);

    // userId 기반 프로필 이미지 경로 반환
    String getUserProfileImagePath(String userId);

    // userId 기반 닉네임 반환
    String getUserNickname(String userId);
}