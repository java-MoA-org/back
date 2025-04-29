package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.alert.CommentAlertRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;

public interface AlertService {
    ResponseEntity<ResponseDto> commentAlertPost(CommentAlertRequestDto alertRequestDto, String userId);
}
