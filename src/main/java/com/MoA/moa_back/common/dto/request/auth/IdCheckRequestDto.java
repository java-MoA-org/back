package com.MoA.moa_back.common.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdCheckRequestDto {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$")
    private String userId;
}
