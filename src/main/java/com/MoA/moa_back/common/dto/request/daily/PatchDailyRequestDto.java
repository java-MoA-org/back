package com.MoA.moa_back.common.dto.request.daily;

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
public class PatchDailyRequestDto {
  
  @NotBlank
  @Size(max = 50)
  private String title;

  @NotBlank
  @Size(max = 2000)
  private String content;
  private String location;

  private String detailLocation;

  private List<String> imageList;

}
