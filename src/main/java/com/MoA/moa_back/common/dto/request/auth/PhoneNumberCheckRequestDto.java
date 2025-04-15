package com.MoA.moa_back.common.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberCheckRequestDto {

    @NotNull
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$")
    private String userPhoneNumber;
    
}