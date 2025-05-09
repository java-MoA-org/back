package com.MoA.moa_back.common.dto.request.usedtrade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUsedTradeRequestDto {

  private String title;
  private String content;
  private Integer price;
  private String location;
  private String detailLocation;
  private List<MultipartFile> imageList;
  private List<String> removeImageUrls;
}
