package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.MoA.moa_back.common.dto.request.daily.PatchDailyRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyCommentRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetDailyResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetDailyListResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetLikedUserListResponseDto;
import com.MoA.moa_back.service.DailyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/daily")
@RequiredArgsConstructor
public class DailyController {
  
  private final DailyService dailyService;

  // API: 일상 게시글 작성 //
  @PostMapping({"", "/"})
  public ResponseEntity<ResponseDto> postDaily(
    @RequestBody @Valid PostDailyRequestDto requestBody
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = dailyService.postDailyBoard(requestBody, userId);
    return response;
  }

  // API: 일상 게시글 목록 조회 (페이지네이션) //
  @GetMapping("/list/{pageNumber}")
  public ResponseEntity<? super GetDailyListResponseDto> getDailyList(
    @PathVariable("pageNumber") Integer pageNumber
  ) {
    ResponseEntity<? super GetDailyListResponseDto> response = dailyService.getDailyBoardList(pageNumber);
    return response;
  }

  // API: 일상 게시글 상세보기 + 조회수 증가 //
  @GetMapping("/{dailySequence}")
  public ResponseEntity<? super GetDailyResponseDto> getDailyDetail(
    @PathVariable("dailySequence") Integer dailySequence
  ) {
    ResponseEntity<? super GetDailyResponseDto> response = dailyService.getDailyBoardDetail(dailySequence);
    return response;
  }

  // API: 일상 게시글 수정 //
  @PatchMapping("/{dailySequence}")
  public ResponseEntity<ResponseDto> patchDaily(
    @RequestBody @Valid PatchDailyRequestDto requestBody,
    @PathVariable("dailySequence") Integer dailySequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = dailyService.patchDailyBoard(requestBody, dailySequence, userId);
    return response;
  }

  // API: 일상 게시글 삭제 //
  @DeleteMapping("/{dailySequence}")
  public ResponseEntity<ResponseDto> deleteDaily(
    @PathVariable("dailySequence") Integer dailySequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = dailyService.deleteDailyBoard(dailySequence, userId);
    return response;
  }

  // API: 일상 게시글 검색 (제목만) //
  @GetMapping("/search")
  public ResponseEntity<? super GetDailyListResponseDto> searchBoardList(
    @RequestParam(value = "keyword", defaultValue = "") String keyword,
    @RequestParam(value = "page", defaultValue = "1") Integer pageNumber
  ) {
    ResponseEntity<? super GetDailyListResponseDto> response = dailyService.searchDailyBoardList(keyword, pageNumber);
    return response;
  }

  // API: 일상 게시글 좋아요 누르기 or 취소 //
  @PutMapping("/{dailySequence}/likes")
  public ResponseEntity<ResponseDto> toggleDailyLike(
    @PathVariable("dailySequence") Integer dailySequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = dailyService.putDailyBoardLikeCount(dailySequence, userId);
    return response;
  }

  // API: 일상 게시글 좋아요 누른 유저 목록 조회 //
  @GetMapping("/{dailySequence}/list/likes")
  public ResponseEntity<? super GetLikedUserListResponseDto> getLikedUsers(
    @PathVariable("dailySequence") Integer dailySequence
  ) {
    ResponseEntity<? super GetLikedUserListResponseDto> response = dailyService.getDailyBoardLikedUsers(dailySequence);
    return response;
  }

  // API: 일상 게시글 댓글 작성 //
  @PostMapping("/{dailySequence}/comments")
  public ResponseEntity<ResponseDto> postDailyComment(
    @RequestBody @Valid PostDailyCommentRequestDto requestBody,
    @PathVariable("dailySequence") Integer dailySequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = dailyService.postDailyBoardComment(requestBody, dailySequence, userId);
    return response;
  }

  // API: 일상 게시글 댓글 삭제 //
  @DeleteMapping("/{commentSequence}/comments")
  public ResponseEntity<ResponseDto> deleteDailyComment(
    @PathVariable("commentSequence") Integer commentSequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = dailyService.deleteDailyComment(commentSequence, userId);
    return response;
  }

}
