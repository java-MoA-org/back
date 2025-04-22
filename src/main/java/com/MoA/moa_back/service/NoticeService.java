package com.MoA.moa_back.service;

import com.MoA.moa_back.common.dto.request.notice.*;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface NoticeService {

    ResponseEntity<ResponseDto> postNotice(PostNoticeRequestDto dto);

    ResponseEntity<ResponseDto> patchNotice(int id, PatchNoticeRequestDto dto);

    ResponseEntity<ResponseDto> deleteNotice(int id);

    ResponseEntity<?> getNotice(int id);           // 실제 구현에서 GetNoticeResponseDto 반환

    ResponseEntity<ResponseDto> getNoticeList(int page);          // 실제 구현에서 GetNoticeListResponseDto 반환
}