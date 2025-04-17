package com.MoA.moa_back.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadProfileImage(MultipartFile file);
}
