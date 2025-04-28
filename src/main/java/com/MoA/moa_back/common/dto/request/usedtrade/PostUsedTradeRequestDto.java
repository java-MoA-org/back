package com.MoA.moa_back.common.dto.request.usedtrade;

import java.time.LocalDateTime;
import java.util.List;

import com.MoA.moa_back.common.enums.ItemTypeTag;
import com.MoA.moa_back.common.enums.UsedItemStatusTag;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;

  @NotBlank
  @Size(max = 50)
  private String title;

  @NotBlank
  @Size(max = 2000)
  private String content;

  @NotNull
  private ItemTypeTag itemTypeTag; // 물건 타입 enum

  @NotNull
  private UsedItemStatusTag usedItemStatusTag; // 물건 상태 enum

  @NotNull
  private Integer price;

  private String location;

  private String detailLocation;

  @Size(min = 1)
  private List<String> imageList;
  

}
