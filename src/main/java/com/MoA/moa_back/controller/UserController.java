package com.MoA.moa_back.controller;

import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;
import com.MoA.moa_back.common.dto.response.user.SearchUserResponseDto;
import com.MoA.moa_back.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 검색 API - 주어진 키워드를 포함한 닉네임을 가진 유저 목록을 반환
    @GetMapping("/search")
    public SearchUserResponseDto searchUser(@RequestParam("keyword") String keyword) {
        return userService.searchUser(keyword);
    }

    // userId를 기반으로 해당 유저의 전체 정보(닉네임, 이메일, 이미지 등)를 반환
    @GetMapping("/{userId}")
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfoById(@PathVariable String userId) {
        return userService.getUserInfoById(userId);
    }

    // userId를 기반으로 해당 유저의 프로필 이미지 경로를 반환
    // 값이 없거나 기본 이미지이면 "default-profile" 문자열을 반환 (HTTP 200 OK)
    @GetMapping("/{userId}/profile-image")
    public ResponseEntity<String> getUserProfileImage(@PathVariable String userId) {
        String imagePath = userService.getUserProfileImagePath(userId);
        if (imagePath == null || imagePath.isEmpty()) {
            return ResponseEntity.ok("default-profile");
        }
        return ResponseEntity.ok(imagePath);
    }

    // userId를 기반으로 해당 유저의 닉네임을 반환
    // 존재하지 않으면 404 응답
    @GetMapping("/{userId}/nickname")
    public ResponseEntity<String> getUserNickname(@PathVariable String userId) {
        String nickname = userService.getUserNickname(userId);
        if (nickname == null || nickname.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nickname);
    }
}