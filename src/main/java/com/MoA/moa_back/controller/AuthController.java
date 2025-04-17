package com.MoA.moa_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.MoA.moa_back.common.dto.request.auth.EmailCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.IdCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.PhoneNumberCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignInRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignUpRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.auth.SignInResponseDto;
import com.MoA.moa_back.common.dto.response.auth.TokenRefreshResponseDto;
import com.MoA.moa_back.service.AuthService;
import com.MoA.moa_back.service.ImageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final ImageService imageService;

    // 아이디 중복 확인
    @PostMapping("/id/check")
    public ResponseEntity<ResponseDto> idCheck(@RequestBody @Valid IdCheckRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = authService.idCheck(requestBody);
        return response;
    }

    // 닉네임 중복 확인
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
    
    @PostMapping("/profileImage/upload")
    public String upload(
        @RequestParam("file") MultipartFile file
    ) {
        String url = imageService.uploadProfileImage(file);
        return url;
    }
    

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = authService.signUp(requestBody);
        return response;
    }
    
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody, HttpServletResponse response) {
        ResponseEntity<? super SignInResponseDto> responseEntity = authService.signIn(requestBody, response);
        return responseEntity;
    }

    @PostMapping("/refresh")
    public ResponseEntity<? super TokenRefreshResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<? super TokenRefreshResponseDto> responseEntity = authService.refreshToken(request, response);
        return responseEntity;
    }
    
    @PostMapping("/sign-out")
    public ResponseEntity<ResponseDto> signOut(HttpServletResponse response) {
        
        ResponseEntity<ResponseDto> responseDto = authService.signOut(response);
        
        return responseDto;
    }
    
}
