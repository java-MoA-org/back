package com.MoA.moa_back.common.dto.request.board;

import java.time.LocalDateTime;
import java.util.List;

import com.MoA.moa_back.common.enums.BoardTagType;
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
public class PostBoardRequestDto {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;

  @NotBlank
  @Size(max=50)
  private String title;

  @NotBlank
  @Size(max=4000)
  private String content;

  private String location;

  private String detailLocation;

  @Size(max=5)
  private List<String> imageList;

  @NotNull 
  private BoardTagType tag;

}
