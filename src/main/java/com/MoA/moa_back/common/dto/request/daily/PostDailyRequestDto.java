package com.MoA.moa_back.common.dto.request.daily;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class PostDailyRequestDto {
  
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;

  @NotBlank
  @Size(max=50)
  private String title;

  @NotBlank
  @Size(max=2000)
  private String content;

  private String location;

  private String detailLocation;

  private List<String> imageList;

}
