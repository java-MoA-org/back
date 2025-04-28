package com.MoA.moa_back.common.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import com.MoA.moa_back.common.dto.request.auth.SignUpRequestDto;
import com.MoA.moa_back.common.dto.request.user.PatchUserInfoRequestDto;
import com.MoA.moa_back.common.enums.UserRole; 

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity(name = "user")
@Table(name = "user")
@ToString
public class UserEntity {

  @Id
  private String userId;
  private String userPassword;
  private String joinType;
  private String userNickname;
  private String profileImage;
  private String userIntroduce;
  private String userEmail;
  private String userPhoneNumber;
  @Enumerated(EnumType.STRING)
  private UserRole userRole = UserRole.USER; // 기본값 USER로 추가

  public UserEntity(SignUpRequestDto dto) {
    this.userId = dto.getUserId();
    this.userPassword = dto.getUserPassword();
    this.joinType = dto.getJoinType();
    this.userNickname = dto.getUserNickname();
    this.profileImage = dto.getProfileImage();
    this.userEmail = dto.getUserEmail();
    this.userPhoneNumber = dto.getUserPhoneNumber();
    this.userIntroduce = dto.getUserIntroduce();
    this.userRole = UserRole.USER; // 회원가입 시 기본 USER 권한 부여
  }

  // 🔧 수정 파트
  public void patch(PatchUserInfoRequestDto dto) {
    if (dto.getUserNickname() != null && !dto.getUserNickname().equals(this.userNickname)) {
      this.userNickname = dto.getUserNickname();
    }
    // 자기소개가 null이 아니고, 기존 값과 다를 때만 변경
    if (dto.getUserIntroduce() != null && !dto.getUserIntroduce().equals(this.userIntroduce)) {
      this.userIntroduce = dto.getUserIntroduce();
    }
    // 전화번호가 null이 아니고, 기존 값과 다를 때만 변경
    if (dto.getUserPhoneNumber() != null && !dto.getUserPhoneNumber().equals(this.userPhoneNumber)) {
      this.userPhoneNumber = dto.getUserPhoneNumber();
    }
    // 프로필 사진이 null이 아니고, 기존 값과 다를 때만 변경
    if (dto.getProfileImage() != null) {
      if (!dto.getProfileImage().equals(this.profileImage)) {
        this.profileImage = dto.getProfileImage();
      }
    }
  }

  // 현재 사용자 권한 반환
  public UserRole getUserRole() {
    return this.userRole;
  }
}