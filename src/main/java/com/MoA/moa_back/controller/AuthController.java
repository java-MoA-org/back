package com.MoA.moa_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.MoA.moa_back.common.dto.request.auth.EmailCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.EmailCodeVerifyRequestDto;
import com.MoA.moa_back.common.dto.request.auth.IdCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.PatchPasswordRequestDto;
import com.MoA.moa_back.common.dto.request.auth.PhoneNumberCheckRequestDto;
import com.MoA.moa_back.common.dto.request.auth.PhoneNumberCodeVerifyRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignInRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignUpRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.auth.EmailVerifyResponseDto;
import com.MoA.moa_back.common.dto.response.auth.PhoneNumberVerifyResponseDto;
import com.MoA.moa_back.common.dto.response.auth.SignInResponseDto;
import com.MoA.moa_back.common.dto.response.auth.TokenRefreshResponseDto;
import com.MoA.moa_back.common.dto.response.auth.FindIdResponseDto;
import com.MoA.moa_back.common.dto.response.auth.UserInfoResponseDto;
import com.MoA.moa_back.service.AuthService;
import com.MoA.moa_back.service.ImageService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



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

    @PostMapping("/email/verify/require")
    public ResponseEntity<? super EmailVerifyResponseDto> emailVeriyfyRequire(@RequestBody EmailCheckRequestDto requestDto) {
        ResponseEntity<? super EmailVerifyResponseDto> responseDto = authService.emailVerifyRequire(requestDto);
        
        return responseDto;
    }

    @PostMapping("/email/verify")
    public ResponseEntity<ResponseDto> verifyEmailCode(@RequestBody EmailCodeVerifyRequestDto dto) {
        ResponseEntity<ResponseDto> response = authService.verifyEmailCode(dto);
        return response;
    }

    
    
    

    // 전화번호 중복 확인
    @PostMapping("/phone/verify/require")
    public ResponseEntity<? super PhoneNumberVerifyResponseDto> phoneNumberCheck(@RequestBody @Valid PhoneNumberCheckRequestDto requestDto) {
        ResponseEntity<? super PhoneNumberVerifyResponseDto> response = authService.phoneNumberVerifyRequire(requestDto);
        return response;
    }
    
    @PostMapping("/phone/verify")
    public ResponseEntity<ResponseDto> verifyPhoneNumberCode(@RequestBody PhoneNumberCodeVerifyRequestDto dto) {
        ResponseEntity<ResponseDto> response = authService.verifyPhoneNumberCode(dto);
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

    @PostMapping("/sign-out")
    public ResponseEntity<ResponseDto> signOut(@AuthenticationPrincipal String userId, HttpServletResponse response) {
        ResponseEntity<ResponseDto> responseEntity = authService.signOut(response);
        
        return responseEntity;
    }
    

    @PostMapping("/refresh")
    public ResponseEntity<? super TokenRefreshResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<? super TokenRefreshResponseDto> responseEntity = authService.refreshToken(request, response);
        return responseEntity;
    }
    
    @PostMapping("/find-id/email/verify/require")
    public ResponseEntity<? super EmailVerifyResponseDto> emailVerify(@RequestBody EmailCheckRequestDto requestDto) {
        ResponseEntity<? super EmailVerifyResponseDto> responseEntity = authService.verifyEmail(requestDto);
        return responseEntity;
    }
    
    @PostMapping("/find-id/email/verify")
    public ResponseEntity<? super FindIdResponseDto> findIdEmailVerify(@RequestBody EmailCodeVerifyRequestDto requestBody) {
        ResponseEntity<? super FindIdResponseDto> response = authService.verifyEmailVC(requestBody);
        return response;
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<ResponseDto> patchUserPassword(@RequestBody PatchPasswordRequestDto requestBody, @PathVariable("userId") String userId){
        ResponseEntity<ResponseDto> response = authService.patchPassword(requestBody, userId);
        return response;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<? super UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal String userId) {
        ResponseEntity<? super UserInfoResponseDto> response = authService.getUserInfo(userId);
        System.out.println(response.getBody());
        return response;
    }
    
    
}
