package com.MoA.moa_back.service.implement;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.MoA.moa_back.service.ImageService;

@Service
public class ImageServiceImplement implements ImageService{

    @Value("${file.board-path}")
    private String boardPath;

    @Value("${file.profile-path}")
    private String profilePath;

    @Value("${file.profile-url}")
    private String profileUrl;

    @Override
    public String uploadProfileImage(MultipartFile file) {
        if(file.isEmpty()) return null;
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = profilePath + saveFileName;

        try {
            file.transferTo(new File(savePath));
        } catch(Exception exception) {
            exception.printStackTrace();
            return null;
        }

        String url = profileUrl + saveFileName;
        return url;
    }
    
}
