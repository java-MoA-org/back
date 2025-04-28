package com.MoA.moa_back.common.dto.request.user;

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

    // 프로필 이미지 변경
    @Pattern(regexp = "^https?://.+\\.(?i)(jpg|jpeg|png|gif|bmp|webp)$")
    private String profileImage;
}
