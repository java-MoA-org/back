package com.MoA.moa_back.common.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private UserRole userRole = UserRole.USER;  // 기본 권한: USER

    // 🔵 PATCH: 유저 정보 수정
    public void patch(PatchUserInfoRequestDto dto) {
        this.userNickname = dto.getUserNickname();
        this.userIntroduce = dto.getUserIntroduce();
        this.userEmail = dto.getUserEmail();
        this.profileImage = dto.getProfileImage();
    }

    // 🔵 회원가입 생성자
    public UserEntity(SignUpRequestDto dto) {
        this.userId = dto.getUserId();
        this.userPassword = dto.getUserPassword();
        this.joinType = dto.getJoinType();
        this.userNickname = dto.getUserNickname();
        this.profileImage = dto.getProfileImage();
        this.userEmail = dto.getUserEmail();
        this.userPhoneNumber = dto.getUserPhoneNumber();
        this.userIntroduce = dto.getUserIntroduce();
        this.userRole = UserRole.USER;
    }

    // 유저 권한 반환
    public UserRole getUserRole() {
        return this.userRole;
    }

    public String getUserProfileImage() {
        if (this.profileImage == null || this.profileImage.isEmpty()) {
            return null;  
        }
        if (this.profileImage.startsWith("http")) {
            return this.profileImage;
        }
        return "http://localhost:4000/profile/file/" + this.profileImage;
    }
}//테스트 