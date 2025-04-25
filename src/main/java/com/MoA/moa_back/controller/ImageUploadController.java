package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.service.ImageUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageUploadController {

  private final ImageUploadService imageUploadService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseDto> uploadImage(
    @RequestParam("file") MultipartFile file,
    @RequestParam(value = "type", defaultValue = "board") String type
  ) {
    return imageUploadService.uploadImage(file, type);
  }
}
