package com.MoA.moa_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.request.EmailCheckRequestDto;
import com.MoA.moa_back.common.dto.request.IdCheckRequestDto;
import com.MoA.moa_back.common.dto.request.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.request.PhoneNumberCheckRequestDto;
import com.MoA.moa_back.common.dto.request.SignInRequestDto;
import com.MoA.moa_back.common.dto.request.SignUpRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.SignInResponseDto;
import com.MoA.moa_back.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    // 아이디 중복 확인
    @PostMapping("/id/check")
    public ResponseEntity<ResponseDto> idCheck(@RequestBody @Valid IdCheckRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = authService.idCheck(requestBody);
        return response;
    }

    // 비밀번호 중복 확인
    @PostMapping("/nickname/check")
    public ResponseEntity<ResponseDto> nicknameCheck(@RequestBody @Valid NicknameCheckRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = authService.nicknameCheck(requestBody);
        return response;
    }

    // 이메일 중복 확인
    @PostMapping("/email/check")
    public ResponseEntity<ResponseDto> emailCheck(@RequestBody @Valid EmailCheckRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = authService.emailCheck(requestBody);
        return response;
    }
    
    // 전화번호 중복 확인
    @PostMapping("/phone/check")
    public ResponseEntity<ResponseDto> phoneNumberCheck(@RequestBody @Valid PhoneNumberCheckRequestDto requestDto) {
        ResponseEntity<ResponseDto> response = authService.phoneNumberCheck(requestDto);
        return response;
    }
    
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = authService.signUp(requestBody);
        return response;
    }
    
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }
    
}
