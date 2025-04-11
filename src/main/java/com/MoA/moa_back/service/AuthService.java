package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// 주석 추가
import com.MoA.moa_back.common.dto.request.EmailCheckRequestDto;
import com.MoA.moa_back.common.dto.request.IdCheckRequestDto;
import com.MoA.moa_back.common.dto.request.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.request.PhoneNumberCheckRequestDto;
import com.MoA.moa_back.common.dto.request.SignUpRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;

public interface AuthService {
    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto requestDto);
    ResponseEntity<ResponseDto> nicknameCheck(NicknameCheckRequestDto requestDto);
    ResponseEntity<ResponseDto> emailCheck(EmailCheckRequestDto requestDto);
    ResponseEntity<ResponseDto> phoneNumberCheck(PhoneNumberCheckRequestDto requestDto);
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto requestDto);
}
