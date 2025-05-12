package com.MoA.moa_back.common.dto.request.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageReadRequestDto {
    private String userId;
    private String partnerId;
}