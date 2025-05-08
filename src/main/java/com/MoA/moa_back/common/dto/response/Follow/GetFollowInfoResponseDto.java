package com.MoA.moa_back.common.dto.response.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;

@Getter
@AllArgsConstructor
public class GetFollowInfoResponseDto extends ResponseDto {
  // 자신을 팔로우하는 유저들의 상세 정보
  private List<GetUserFollowInfoResponseDto> followers;
  // 자신이 팔로우하는 유저들의 상세 정보
  private List<GetUserFollowInfoResponseDto> followees;

  public static ResponseEntity<GetFollowInfoResponseDto> success(
      List<GetUserFollowInfoResponseDto> followers,
      List<GetUserFollowInfoResponseDto> followees) {
    GetFollowInfoResponseDto body = new GetFollowInfoResponseDto(followers, followees);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
