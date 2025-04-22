package com.MoA.moa_back.common.dto.request.auth;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PhoneNumberCodeVerifyRequestDto {
    private String userPhoneNumber;
    private String userPhoneNumberVC;
    private String userPhoneNumberToken;
}
