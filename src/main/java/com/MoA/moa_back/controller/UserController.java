package com.MoA.moa_back.controller;

import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;
import com.MoA.moa_back.common.dto.response.user.SearchUserResponseDto;
import com.MoA.moa_back.service.UserService;
import com.MoA.moa_back.common.dto.response.ResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ 유저 검색 API - 닉네임 등 키워드를 기반으로 유저 리스트 반환
    @GetMapping("/search")
    public SearchUserResponseDto searchUser(@RequestParam("keyword") String keyword) {
        return userService.searchUser(keyword);
    }

    // ✅ userId 기반 전체 유저 정보 조회 API (닉네임, 이메일, 소개, 이미지, 관심사 등 포함)
    @GetMapping("/{userId}")
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfoById(@PathVariable String userId) {
        return userService.getUserInfoById(userId);
    }

    // ✅ userId 기반 프로필 이미지 경로 단독 조회 API (404 반환 가능성 있음)
    @GetMapping("/{userId}/profile-image")
    public ResponseEntity<String> getUserProfileImage(@PathVariable String userId) {
        String imagePath = userService.getUserProfileImagePath(userId);
        if (imagePath == null || imagePath.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imagePath);
    }

    // ✅ userId 기반 닉네임 단독 조회 API (추가 구현 필요시 사용)
    @GetMapping("/{userId}/nickname")
    public ResponseEntity<String> getUserNickname(@PathVariable String userId) {
        String nickname = userService.getUserNickname(userId);
        if (nickname == null || nickname.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nickname);
    }
}