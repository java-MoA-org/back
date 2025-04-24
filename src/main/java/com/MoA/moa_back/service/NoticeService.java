package com.MoA.moa_back.service;

import com.MoA.moa_back.common.dto.request.notice.*;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface NoticeService {

    // method: 공지사항 등록 //
    ResponseEntity<ResponseDto> postNotice(PostNoticeRequestDto dto);
    // method: 공지사항 수정 //
    ResponseEntity<ResponseDto> patchNotice(int id, PatchNoticeRequestDto dto);
    // method: 공지사항 삭제 //
    ResponseEntity<ResponseDto> deleteNotice(int id);
    
    // method: 공지사항 단건 조회 (조회수 증가 포함) //
    ResponseEntity<?> getNotice(int id); // 실제 구현에서 GetNoticeResponseDto 반환
    // method: 공지사항 목록 조회 (페이징 + 최신순) //
    ResponseEntity<ResponseDto> getNoticeList(int page); // 실제 구현에서 GetNoticeListResponseDto 반환
}