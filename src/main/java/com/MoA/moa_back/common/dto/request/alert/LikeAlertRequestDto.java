package com.MoA.moa_back.common.dto.request.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeAlertRequestDto {
    private String boardType;
    private Integer sequence;
}
