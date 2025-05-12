package com.MoA.moa_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.service.ImageUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageUploadController {

  private final ImageUploadService imageUploadService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseDto> uploadImages(
    @RequestParam("file") List<MultipartFile> files,
    @RequestParam(value = "type", defaultValue = "board") String type
  ) {
    List<String> urls = imageUploadService.uploadImages(files, type);
    return ResponseDto.success(HttpStatus.OK, urls);
  }

  @GetMapping(value = "/{fileName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
  public ResponseEntity<Resource> getImageFile(
    @PathVariable("fileName") String fileName,
    @RequestParam(value = "type", defaultValue = "board") String type) {
    Resource resource = imageUploadService.getImageFile(fileName, type);
    if (resource == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().body(resource);
  }

}
