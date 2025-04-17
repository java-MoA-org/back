package com.MoA.moa_back.common.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatchUserInfoRequestDto {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$")
    private String userNickname;

    private String userIntroduce;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$")
    private String userPhoneNumber;

    // 새 비밀번호
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+~`{}\\[\\]:;\"'<>,.?/\\\\|\\-]).{8,12}$")
    private String userPassword;

    // 현재 비밀번호: 비밀번호 변경 시에만 입력 (검증용)
    private String currentPassword;

    // 프로필 이미지 변경
    @Pattern(regexp = "^https?://.+\\.(?i)(jpg|jpeg|png|gif|bmp|webp)$")
    private String profileImage;
}
