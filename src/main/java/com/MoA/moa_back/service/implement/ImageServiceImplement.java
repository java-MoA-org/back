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

    @Value("${file.default-profile-url}") // 디폴트 프로필 이미지 URL 설정
    private String defaultProfileUrl;

    @Override
    public String uploadProfileImage(MultipartFile file) {
        // 파일이 비어있으면 기본 프로필 이미지 URL 반환
        if (file == null || file.isEmpty()) return defaultProfileUrl;

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = profilePath + saveFileName;

        try {
            // 지정 경로에 파일 저장
            file.transferTo(new File(savePath));
        } catch (Exception exception) {
            exception.printStackTrace();
            // 업로드 실패 시에도 기본 이미지 URL 반환
            return defaultProfileUrl;
        }

        // 업로드 성공 시 해당 이미지 URL 반환
        return profileUrl + saveFileName;
    }
    
}
