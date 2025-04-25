package com.MoA.moa_back.common.dto.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PatchPasswordUserPageRequestDto {
  @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+~`{}\\[\\]:;\"'<>,.?/\\\\|\\-]).{8,12}$|^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
    private String userPassword;

    @NotNull
    private String currentPassword;
}
