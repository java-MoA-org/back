package com.MoA.moa_back.common.dto.request.auth;

import com.MoA.moa_back.common.dto.request.user.Interests;

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
public class SignUpRequestDto {
    
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$|^(NAVER|KAKAO)_[a-zA-Z0-9]{10}$")
    private String userId;
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+~`{}\\[\\]:;\"'<>,.?/\\\\|\\-]).{8,12}$|^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
    private String userPassword;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$")
    private String userNickname;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String userEmail;
    @NotNull
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$")
    private String userPhoneNumber;
    
    @NotNull
    @Pattern(regexp = "^(NORMAL|KAKAO|NAVER)$")
    private String joinType;

    @Pattern(regexp = "^https?://.+\\.(?i)(jpg|jpeg|png|gif|bmp|webp)$")
    private String profileImage;
    
    @Pattern(regexp = "^.{0,50}$")
    private String userIntroduce;

    private Interests interests;
}
