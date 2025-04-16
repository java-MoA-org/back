package com.MoA.moa_back.common.dto.request.board;

import java.util.List;

import com.MoA.moa_back.common.enums.BoardTagType;

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

  private String creationDate;

  @NotBlank
  @Size(max=50)
  private String title;

  @NotBlank
  @Size(max=2000)
  private String content;

  private String location;

  private String detailLocation;

  private List<String> imageList;
  
  @NotNull 
  private BoardTagType tag;

}
