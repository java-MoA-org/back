package com.MoA.moa_back.common.entity;

import com.MoA.moa_back.common.dto.request.SignUpRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="user")
@Table(name="user")
@ToString
public class UserEntity {
  @Id
  private String userId;
  private String userPassword;
  private String joinType;
  private String snsId;
  private String userNickname;
  private String profileImage;
  private String userIntroduce;
  private String userEmail;
  private String userPhoneNumber;

  public UserEntity(SignUpRequestDto dto){
    this.userId = dto.getUserId();
    this.userPassword = dto.getUserPassword();
    this.joinType = dto.getJoinType();
    this.snsId = dto.getSnsId();
    this.userNickname = dto.getUserNickname();
    this.profileImage = dto.getProfileImage();
    this.userEmail = dto.getUserEmail();
    this.userPhoneNumber = dto.getUserPhoneNumber();
    this.userIntroduce = dto.getUserIntroduce();
  }

}
