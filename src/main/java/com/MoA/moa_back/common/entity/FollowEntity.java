package com.MoA.moa_back.common.entity;

import com.MoA.moa_back.common.dto.request.Follow.PostFollowRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "follow")
@Table(name = "follow")
@IdClass(FollowId.class)
public class FollowEntity {
  @Id
  private String follower;
  @Id
  private String followee;

  public FollowEntity(String followeeUserId, String followerUserId){
    this.followee = followeeUserId;
    this.follower = followerUserId;
  }
}
