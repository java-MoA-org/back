package com.MoA.moa_back.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
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

  @Value("${file.usedtrade-image-path}")
  private String usedTradePath;

  @Value("${file.profile-path}")
  private String profilePath;

  @Value("${file.board-image-url}")
  private String boardUrl;

  @Value("${file.daily-image-url}")
  private String dailyUrl;

  @Value("${file.usedtrade-image-url}")
  private String usedTradeUrl;

  @Value("${file.profile-url}")
  private String profileUrl;

  public ResponseEntity<ResponseDto> uploadImage(MultipartFile file, String type) {
    String savePath;
    String accessUrl;

    switch (type.toLowerCase()) {
      case "daily":
        savePath = dailyPath;
        accessUrl = dailyUrl;
        break;
      case "usedtrade":
        savePath = usedTradePath;
        accessUrl = usedTradeUrl;
        break;
      case "profile":
        savePath = profilePath;
        accessUrl = profileUrl;
        break;
      case "board":
      default:
        savePath = boardPath;
        accessUrl = boardUrl;
        break;
    }

    try {
      String originalFileName = file.getOriginalFilename();
      if (originalFileName == null) {
        return ResponseDto.validationFail();
      }

      String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
      if (!fileExtension.equals(".jpg") && !fileExtension.equals(".png")) {
        return ResponseDto.validationFail();
      }

      long maxSize = 10 * 1024 * 1024;
      if (file.getSize() > maxSize) {
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
}
