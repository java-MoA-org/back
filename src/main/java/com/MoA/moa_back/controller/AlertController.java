package com.MoA.moa_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.request.alert.CommentAlertRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.service.AlertService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/alert")
@RequiredArgsConstructor
public class AlertController {
    
    private final AlertService alertService;

    @PostMapping("/comment")
    public ResponseEntity<ResponseDto> commentAlert(@RequestBody CommentAlertRequestDto requestDto, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = alertService.commentAlertPost(requestDto, userId);
        return response;
    }
    

}
