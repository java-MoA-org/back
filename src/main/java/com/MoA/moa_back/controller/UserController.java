package com.MoA.moa_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.response.user.GetUserInfoResponseDto;
import com.MoA.moa_back.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    
    private UserService userService;

    @GetMapping("/info")
    public ResponseEntity<? super GetUserInfoResponseDto> getMethodName(@AuthenticationPrincipal String userId) {
        ResponseEntity<? super GetUserInfoResponseDto> response = userService.getUserInfo(userId);
        return response;
        
    }
    

}
