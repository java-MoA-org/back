package com.MoA.moa_back.common.dto.response.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetNoticeListResponseDto {
    private List<GetNoticeResponseDto> noticeList;
}