package com.MoA.moa_back.common.entity;


import org.springframework.security.crypto.password.PasswordEncoder;

import com.MoA.moa_back.common.dto.request.PatchUserInfoRequestDto;
import com.MoA.moa_back.common.dto.request.auth.SignUpRequestDto;
import com.MoA.moa_back.common.dto.request.user.Interests;

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
@Entity(name="user")
@Table(name="user")
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

  public UserEntity(SignUpRequestDto dto){
    this.userId = dto.getUserId();
    this.userPassword = dto.getUserPassword();
    this.joinType = dto.getJoinType();
    this.userNickname = dto.getUserNickname();
    this.profileImage = dto.getProfileImage();
    this.userEmail = dto.getUserEmail();
    this.userPhoneNumber = dto.getUserPhoneNumber();
    this.userIntroduce = dto.getUserIntroduce();
  }

  // 수정 파트
  public void patch(PatchUserInfoRequestDto dto, PasswordEncoder passwordEncoder) {
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
  if (dto.getProfileImage() != null && !dto.getProfileImage().equals(this.profileImage)) {
    this.profileImage = dto.getProfileImage();
} else {
    this.profileImage = null; // null이면 null값 들어가게
}
  // 새 비밀번호가 null/공백이 아니고, 기존 암호화된 비밀번호와 다를 때만 변경
  if (dto.getUserPassword() != null && !dto.getUserPassword().isBlank()) {
      String encodedNewPassword = passwordEncoder.encode(dto.getUserPassword());
      // 같은 비밀번호라도 BCrypt는 매번 다른 해시가 나오므로 비교 의미 없음
      // 이미 Service 단에서 인증되었으니 그냥 저장하면 됨
      this.userPassword = encodedNewPassword;
  }
}

}
