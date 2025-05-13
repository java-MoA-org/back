package com.MoA.moa_back.service.implement;

import com.MoA.moa_back.service.MessageImageUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class MessageImageUploadServiceImplement implements MessageImageUploadService {

    @Value("${file.message-image-path}")
    private String uploadPath;

    @Value("${file.message-image-url}")
    private String imageUrl;

    @Override
    public String upload(MultipartFile file, String userId, String category) throws Exception {
        if (file.isEmpty()) throw new Exception("파일이 비어있습니다.");

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueName = UUID.randomUUID().toString() + extension;

        File dest = new File(uploadPath + uniqueName);
        file.transferTo(dest);

        return imageUrl + uniqueName;
    }
}