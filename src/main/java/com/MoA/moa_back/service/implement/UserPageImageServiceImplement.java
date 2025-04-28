package com.MoA.moa_back.service.implement;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.MoA.moa_back.service.UserPageImageService;

@Service
public class UserPageImageServiceImplement implements UserPageImageService{

    @Value("${file.path}")
    private String filePath;
    @Value("${file.url}")
    private String fileUrl;

    @Override
    public String upload(MultipartFile file) { // 파일 업로드 성공 시 접근 가능한 URL 반환이 목적


        // description : 빈 파일인지 확인 //
        if(file.isEmpty()) return null;

        // description : 원본 파일명 구하기 //
        String originalFileName = file.getOriginalFilename();

        // description : 확장자 구하기 //
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 맨마지막 점의 위치를 파악을 통해 확장자 파악

        // description : UUID 형식의 임의의 파일명 생성 //
        String uuid = UUID.randomUUID().toString();

        // description : 저장 될 파일명 //
        String saveFileName = uuid + extension;
        
        // description : 파일이 저장될 경로 //
        String savePath = filePath + saveFileName; // 어떤 경로 저장될지

        // description : 파일 저장 //
        try{
            file.transferTo(new File(savePath));

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // description : 파일을 불러올 수 있는 경로 생성 //
        String url = fileUrl + saveFileName;
        return url;
    }

    @Override
    public Resource getImageFile(String fileName) {
        
        Resource resource = null;
        // description : 파일 저장 경로에 있는 파일 불러오기 //
        try {
            String uri = "file:" + filePath + fileName;
            resource = new UrlResource(uri);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return resource;
    }
  
}
