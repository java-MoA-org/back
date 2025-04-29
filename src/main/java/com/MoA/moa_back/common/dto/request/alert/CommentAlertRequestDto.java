package com.MoA.moa_back.common.dto.request.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentAlertRequestDto {
    private String comment;
    private String boardType;
    private Integer sequence;
}
