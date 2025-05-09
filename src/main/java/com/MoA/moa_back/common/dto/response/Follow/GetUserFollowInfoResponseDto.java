package com.MoA.moa_back.common.dto.response.Follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserFollowInfoResponseDto {
  private String userId;
  private String userNickname;
  private String userIntroduce;
  private String profileImage;
  private boolean isFollowed;
}
