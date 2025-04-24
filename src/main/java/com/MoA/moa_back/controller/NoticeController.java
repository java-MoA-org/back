package com.MoA.moa_back.controller;

import com.MoA.moa_back.common.dto.request.notice.*;
import com.MoA.moa_back.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 목록 조회 (페이징)
    @GetMapping("/list")
    public ResponseEntity<?> getNoticeList(@RequestParam(value = "page", defaultValue = "0") int page) {
        return noticeService.getNoticeList(page);
    }

    // 공지사항 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getNotice(@PathVariable("id") int id) {
        return noticeService.getNotice(id); 
    }

    // 공지사항 등록 (관리자만 가능)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> postNotice(@RequestBody PostNoticeRequestDto dto) {
        return noticeService.postNotice(dto);
    }

    // 공지사항 수정 (관리자만 가능)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> patchNotice(@PathVariable("id") int id, @RequestBody PatchNoticeRequestDto dto) {
        return noticeService.patchNotice(id, dto);
    }

    // 공지사항 삭제 (관리자만 가능)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteNotice(@PathVariable("id") int id) {
        return noticeService.deleteNotice(id);
    }
}