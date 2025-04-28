package com.MoA.moa_back.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UserPageImageService {
  String upload(MultipartFile file);
  Resource getImageFile(String fileName);
} 