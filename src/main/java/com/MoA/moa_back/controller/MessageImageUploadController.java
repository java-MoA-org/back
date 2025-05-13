package com.MoA.moa_back.controller;

import com.MoA.moa_back.service.MessageImageUploadService;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageImageUploadController {

    private final MessageImageUploadService messageImageUploadService;

    @PostMapping("/upload-image")
    public ResponseEntity<ResponseDto> uploadMessageImage(
        @RequestParam("file") MultipartFile file,
        @AuthenticationPrincipal String userId
    ) {
        try {
            String url = messageImageUploadService.upload(file, userId, "message");
            return ResponseDto.success(HttpStatus.OK, url);
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }
    }
}