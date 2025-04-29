package com.MoA.moa_back.common.dto.request.Follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostFollowRequestDto {
  private String followerId;
  private String followeeId;
}
