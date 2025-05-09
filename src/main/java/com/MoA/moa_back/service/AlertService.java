package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.alert.CommentAlertRequestDto;
import com.MoA.moa_back.common.dto.request.alert.LikeAlertRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.alert.GetAlertResponseDto;

public interface AlertService {
    ResponseEntity<? super GetAlertResponseDto> getAlert(String userId);
    ResponseEntity<ResponseDto> commentAlertPost(CommentAlertRequestDto alertRequestDto, String userId);
    ResponseEntity<ResponseDto> likeAlertPost(LikeAlertRequestDto alertRequestDto, String userId);
    ResponseEntity<ResponseDto> readAlertPatch(Integer alertId, String userId);
    ResponseEntity<ResponseDto> readAllAlertPatch(String userId);
    ResponseEntity<ResponseDto> deleteAlert(Integer alertId, String userId);
    ResponseEntity<ResponseDto> deleteAlertAll(String userId);
}
