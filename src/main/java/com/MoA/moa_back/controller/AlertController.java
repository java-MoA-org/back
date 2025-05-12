package com.MoA.moa_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.request.alert.CommentAlertRequestDto;
import com.MoA.moa_back.common.dto.request.alert.LikeAlertRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.alert.GetAlertResponseDto;
import com.MoA.moa_back.service.AlertService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/alert")
@RequiredArgsConstructor
public class AlertController {
    
    private final AlertService alertService;

    // 유저 알림 가져오기
    @GetMapping("")
    public ResponseEntity<? super GetAlertResponseDto> getMethodName(@AuthenticationPrincipal String userId) {
        ResponseEntity<? super GetAlertResponseDto> response = alertService.getAlert(userId);
        return response;
    }
    
    // 댓글 알림 생성
    @PostMapping("/comment")
    public ResponseEntity<ResponseDto> commentAlert(@RequestBody CommentAlertRequestDto requestDto, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = alertService.commentAlertPost(requestDto, userId);
        return response;
    }

    // 좋아요 알림 생성
    @PostMapping("/like")
    public ResponseEntity<ResponseDto> likeAlert(@RequestBody LikeAlertRequestDto requestDto, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = alertService.likeAlertPost(requestDto, userId);
        return response;
    }
    
    // 알림 읽기
    @PatchMapping("/read/{alertId}")
    public ResponseEntity<ResponseDto> readAlert(@PathVariable Integer alertId, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = alertService.readAlertPatch(alertId, userId);
        return response;
    }

    // 모든 알림 읽기
    @PatchMapping("/readAll")
    public ResponseEntity<ResponseDto> readAllAlert(@AuthenticationPrincipal String userId){
        ResponseEntity<ResponseDto> response = alertService.readAllAlertPatch(userId);
        return response;
    }

    // 알림 삭제
    @DeleteMapping("/delete/{alertId}")
    public ResponseEntity<ResponseDto> deleteAlert(@PathVariable("alertId") Integer alertId, @AuthenticationPrincipal String userId){
        ResponseEntity<ResponseDto> response = alertService.deleteAlert(alertId, userId);
        return response;
    }

    // 모든 알림 삭제
    @DeleteMapping("/deleteAll")
    public ResponseEntity<ResponseDto> deleteAllAlert(@AuthenticationPrincipal String userId){
        ResponseEntity<ResponseDto> response = alertService.deleteAlertAll(userId);
        return response;
    }
    

}
