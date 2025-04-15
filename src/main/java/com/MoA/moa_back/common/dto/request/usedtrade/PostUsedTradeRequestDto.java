package com.MoA.moa_back.common.dto.request.usedtrade;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUsedTradeRequestDto {
  
  private String creationDate;

  @NotBlank
  @Size(max=50)
  private String title;

  @NotBlank
  @Size(max=2000)
  private String content;

  @NotBlank
  private String itemTypeTag; // 물건 타입 ex) 전자기기, 옷

  @NotBlank
  private String usedItemStatusTag; // 물건 상태 ex) 사용감 있음 , 새상품, 사용감 없음

  @NotBlank
  private String price;

  private String location;

  private String detailLocation;

  private List<String> imageList;

}
