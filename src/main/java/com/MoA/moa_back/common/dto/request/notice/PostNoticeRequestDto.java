package com.MoA.moa_back.common.dto.request.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostNoticeRequestDto {
    private String title;
    private String content;
}