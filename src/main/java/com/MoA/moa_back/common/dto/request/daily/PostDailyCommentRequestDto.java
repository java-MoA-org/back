package com.MoA.moa_back.common.dto.request.daily;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDailyCommentRequestDto {
  @NotBlank
	private String dailyComment;
}
