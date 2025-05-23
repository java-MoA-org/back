package com.MoA.moa_back.common.dto.response.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikedUserDto {

  private String userId;
  private String profileImage;
  private String userNickname;

  public LikedUserDto(String userId, String profileImage) {
    this.userId = userId;
    this.profileImage = profileImage;
  }

}
