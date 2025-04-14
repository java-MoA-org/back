package com.MoA.moa_back.common.dto.request.board;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBoardCommentRequestDto {
  @NotBlank
	private String boardComment;
}
