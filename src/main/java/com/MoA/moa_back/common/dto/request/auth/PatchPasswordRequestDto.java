package com.MoA.moa_back.common.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatchPasswordRequestDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$|^(NAVER|KAKAO)_[a-zA-Z0-9]{10}$")
    private String userId;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+~`{}\\[\\]:;\"'<>,.?/\\\\|\\-]).{8,12}$|^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
    private String userPassword;
}
