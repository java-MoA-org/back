package com.MoA.moa_back.common.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCodeVerifyRequestDto {
    private String userEmail;
    private String userEmailVC;
    private String emailToken;
}
