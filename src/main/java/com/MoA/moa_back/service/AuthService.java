package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

// 주석 추가
import com.MoA.moa_back.common.dto.request.auth.EmailCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.IdCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.PhoneNumberCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignInRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignUpRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.auth.SignInResponseDto;
import com.MoA.moa_back.common.dto.response.auth.TokenRefreshResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto requestDto);
    ResponseEntity<ResponseDto> nicknameCheck(NicknameCheckRequestDto requestDto);
    ResponseEntity<ResponseDto> emailCheck(EmailCheckRequestDto requestDto);
    ResponseEntity<ResponseDto> phoneNumberCheck(PhoneNumberCheckRequestDto requestDto);
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto requestDto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto requestDto,HttpServletResponse response);
    ResponseEntity<? super TokenRefreshResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response);
    ResponseEntity<ResponseDto> signOut(HttpServletResponse response);
}
