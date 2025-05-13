package com.MoA.moa_back.service;

import org.springframework.web.multipart.MultipartFile;

public interface MessageImageUploadService {
    String upload(MultipartFile file, String userId, String category) throws Exception;
}