package com.MoA.moa_back.common.entity;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.MoA.moa_back.common.dto.request.auth.SignUpRequestDto;
import com.MoA.moa_back.common.dto.request.user.PatchUserInfoRequestDto;
import com.MoA.moa_back.common.enums.UserRole;

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

    // 회원가입용 생성자
    public UserEntity(SignUpRequestDto dto) {
        this.userId = dto.getUserId();
        this.userPassword = dto.getUserPassword();
        this.joinType = dto.getJoinType();
        this.userNickname = dto.getUserNickname();
        this.profileImage = dto.getProfileImage();
        this.userEmail = dto.getUserEmail();
        this.userPhoneNumber = dto.getUserPhoneNumber();
        this.userIntroduce = dto.getUserIntroduce();
        this.userRole = UserRole.USER; // 기본 USER 권한
    }

    // 🔧 유저 정보 수정
    public void patch(PatchUserInfoRequestDto dto) {
        if (dto.getUserNickname() != null && !dto.getUserNickname().equals(this.userNickname)) {
            this.userNickname = dto.getUserNickname();
        }
        if (dto.getUserIntroduce() != null && !dto.getUserIntroduce().equals(this.userIntroduce)) {
            this.userIntroduce = dto.getUserIntroduce();
        }
        if (dto.getUserPhoneNumber() != null && !dto.getUserPhoneNumber().equals(this.userPhoneNumber)) {
            this.userPhoneNumber = dto.getUserPhoneNumber();
        }
        if (dto.getProfileImage() != null && !dto.getProfileImage().equals(this.profileImage)) {
            this.profileImage = dto.getProfileImage();
        }
    }

    // 현재 사용자 권한 반환
    public UserRole getUserRole() {
        return this.userRole;
    }

    public String getUserProfileImage() {
      if (this.profileImage != null && !this.profileImage.isEmpty()) {
          return "http://localhost:4000/profile/file/" + this.profileImage;
      }
      return "http://localhost:4000/profile/file/default-profile.png";  // 프로필 설정을 하지않으면 기본 이미지 URL 반환
  }
}