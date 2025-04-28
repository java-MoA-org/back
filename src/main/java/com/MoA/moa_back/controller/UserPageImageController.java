package com.MoA.moa_back.controller;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.MoA.moa_back.service.UserPageImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-page/images/file")
@RequiredArgsConstructor
public class UserPageImageController {

  private final UserPageImageService userPageImageService;

  @PostMapping("/upload")
    public String upload(
        @RequestParam("file") MultipartFile file // @RequestParam에 file이라고 적으면 key자리에 file 적기
    ) {
        String url = userPageImageService.upload(file);
        return url;
    }

    @GetMapping(value = "/{fileName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public Resource getImageFile(
        @PathVariable("fileName") String fileName
    ) {
        Resource resource = userPageImageService.getImageFile(fileName);
        return resource;
    }
}
