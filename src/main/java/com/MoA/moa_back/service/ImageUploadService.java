package com.MoA.moa_back.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.MoA.moa_back.common.dto.response.ResponseDto;

@Service
public class ImageUploadService {

  @Value("${file.board-image-path}")
  private String boardPath;

  @Value("${file.daily-image-path}")
  private String dailyPath;

  @Value("${file.used-trade-image-path}")
  private String usedTradePath;

  @Value("${file.profile-path}")
  private String profilePath;

  @Value("${file.board-image-url}")
  private String boardUrl;

  @Value("${file.daily-image-url}")
  private String dailyUrl;

  @Value("${file.used-trade-image-url}")
  private String usedTradeUrl;

  @Value("${file.profile-url}")
  private String profileUrl;

  private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

  private String getSavePath(String type) {
    switch (type.toLowerCase()) {
      case "daily":
        return dailyPath;
      case "used-trade":
        return usedTradePath;
      case "profile":
        return profilePath;
      case "board":
      default:
        return boardPath;
    }
  }

  private String getAccessUrl(String type) {
    switch (type.toLowerCase()) {
      case "daily":
        return dailyUrl;
      case "used-trade":
        return usedTradeUrl;
      case "profile":
        return profileUrl;
      case "board":
      default:
        return boardUrl;
    }
  }

  public ResponseEntity<ResponseDto> uploadImage(MultipartFile file, String type) {
    String savePath = getSavePath(type);
    String accessUrl = getAccessUrl(type);

    try {
      String originalFileName = file.getOriginalFilename();
      if (originalFileName == null || originalFileName.isEmpty()) {
        return ResponseDto.validationFail();
      }

      String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
      if (!fileExtension.equals(".jpg") && !fileExtension.equals(".jpeg") && !fileExtension.equals(".png")) {
        return ResponseDto.validationFail();
      }

      if (file.getSize() > MAX_FILE_SIZE) {
        return ResponseDto.validationFail();
      }

      String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

      File dir = new File(savePath);
      if (!dir.exists()) dir.mkdirs();

      Path path = Paths.get(savePath + uniqueFileName);
      Files.write(path, file.getBytes());

      String imageUrl = accessUrl + uniqueFileName;
      return ResponseDto.success(HttpStatus.OK, imageUrl);

    } catch (IOException e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  public List<String> uploadImages(List<MultipartFile> files, String type) {
    List<String> uploadedImageUrls = new ArrayList<>();

    for (MultipartFile file : files) {
      ResponseEntity<ResponseDto> uploadResponse = uploadImage(file, type);
      if (uploadResponse.getStatusCode() == HttpStatus.OK) {
        String uploadedImageUrl = (String) uploadResponse.getBody().getData();
        uploadedImageUrls.add(uploadedImageUrl);
      } else {
        uploadedImageUrls.add(null);
      }
    }

    return uploadedImageUrls;
  }

  public Resource getImageFile(String fileName, String type) {

    String savePath = getSavePath(type);

    Path filePath = Paths.get(savePath + fileName);

    File file = filePath.toFile();
    if (!file.exists()) {
      return null;
    }
    return new FileSystemResource(file);
  }

}
