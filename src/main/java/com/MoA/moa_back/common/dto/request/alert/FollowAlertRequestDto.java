package com.MoA.moa_back.common.dto.request.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowAlertRequestDto {
    private String userId;
    private String followerNickname;
}
