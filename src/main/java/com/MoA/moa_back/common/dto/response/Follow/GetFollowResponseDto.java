package com.MoA.moa_back.common.dto.response.Follow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.FollowEntity;

import lombok.Getter;

@Getter
public class GetFollowResponseDto extends ResponseDto{
  private List<String> followers;
  private List<String> followees;

  private GetFollowResponseDto(List<FollowEntity> followers, List<FollowEntity> followees){
    this.followers = new ArrayList<>(); 
    for(FollowEntity followEntity: followers) {
      String follower = followEntity.getFollowee();
      this.followers.add(follower);
    }
    this.followees = new ArrayList<>();
    for(FollowEntity followEntity : followees){
      String followee = followEntity.getFollower();
      this.followees.add(followee);
    }
  }

  public static ResponseEntity<GetFollowResponseDto> success(List<FollowEntity> followers, List<FollowEntity> followees) {
    GetFollowResponseDto responseBody = new GetFollowResponseDto(followers, followees);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }
}
