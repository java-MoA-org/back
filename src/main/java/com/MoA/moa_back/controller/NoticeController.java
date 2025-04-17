package com.MoA.moa_back.controller;

import com.MoA.moa_back.common.dto.request.notice.*;
import com.MoA.moa_back.common.dto.response.*;
import com.MoA.moa_back.common.dto.response.notice.*;
import com.MoA.moa_back.service.NoticeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public ResponseEntity<?> getNoticeList() {
    return noticeService.getNoticeList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotice(@PathVariable("id") int id) {
        return noticeService.getNotice(id);
    }

    @PostMapping
    public ResponseEntity<?> postNotice(@RequestBody PostNoticeRequestDto dto) {
        return noticeService.postNotice(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> patchNotice(@PathVariable("id") int id, @RequestBody PatchNoticeRequestDto dto) {
        return noticeService.patchNotice(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable("id") int id) {
        return noticeService.deleteNotice(id);
    }
}