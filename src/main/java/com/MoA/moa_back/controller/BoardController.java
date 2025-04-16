package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardCommentRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardListResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardResponseDto;
import com.MoA.moa_back.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
  
  private final BoardService boardService;

  // API: 게시글 작성 //
  @PostMapping({"", "/"})
  public ResponseEntity<ResponseDto> postBoard(
    @RequestBody @Valid PostBoardRequestDto requestBody
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = boardService.postBoard(requestBody, userId);
    return response;
  }

  // API: 게시글 리스트 조회 (태그 기준)
  @GetMapping("/list/{tag}/{pageNumber}")
  public ResponseEntity<? super GetBoardListResponseDto> getBoardListByBoardTag(
    @PathVariable("tag") String tag,
    @PathVariable("pageNumber") Integer pageNumber
  ) {
    ResponseEntity<? super GetBoardListResponseDto> response = boardService.getBoardListByBoardTag(tag, pageNumber);
    return response;
  }

  // API: 게시글 상세보기 + 조회수 증가 //
  @GetMapping("/{boardSequence}")
  public ResponseEntity<? super GetBoardResponseDto> getBoardDetail(
    @PathVariable("boardSequence") Integer boardSequence
  ) {
    ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoardDetail(boardSequence);
    return response;
  }

  // API: 게시글 수정 //
  @PatchMapping("/{boardSequence}")
  public ResponseEntity<ResponseDto> patchBoard(
    @RequestBody @Valid PatchBoardRequestDto requestBody,
    @PathVariable("boardSequence") Integer boardSequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = boardService.patchBoard(requestBody, boardSequence, userId);
    return response;
  }

  // API: 게시글 삭제 //
  @DeleteMapping("/{boardSequence}")
  public ResponseEntity<ResponseDto> deleteBoard(
    @PathVariable("boardSequence") Integer boardSequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = boardService.deleteBoard(boardSequence, userId);
    return response;
  }

  // API: 게시글 검색 (태그별 가능) //
  @GetMapping("/search")
  public ResponseEntity<? super GetBoardListResponseDto> searchBoardList(
    @RequestParam(value = "tag", defaultValue = "ALL") String tag,
    @RequestParam(value = "keyword", defaultValue = "") String keyword,
    @RequestParam(value = "page", defaultValue = "1") Integer pageNumber
  ) {
    ResponseEntity<? super GetBoardListResponseDto> response = boardService.searchBoardList(tag, keyword, pageNumber);
    return response;
  }

  // API: 게시글 좋아요 누르기 or 취소 //
  @PutMapping("/{boardSequence}/likes")
  public ResponseEntity<ResponseDto> toggleBoardLike(
    @PathVariable("boardSequence") Integer boardSequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = boardService.putBoardLikeCount(boardSequence, userId);
    return response;
  }

  // API: 게시글 댓글 작성 //
  @PostMapping("/{boardSequence}/comments")
  public ResponseEntity<ResponseDto> postBoardComment(
    @RequestBody @Valid PostBoardCommentRequestDto requestBody,
    @PathVariable("boardSequence") Integer boardSequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    ResponseEntity<ResponseDto> response = boardService.postBoardComment(requestBody, boardSequence, userId);
    return response;
  }

  // API: 게시글 댓글 삭제 //
  @DeleteMapping("/{commentSequence}/comments")
  public ResponseEntity<ResponseDto> deleteDailyComment(
    @PathVariable("commentSequence") Integer commentSequence
    // @AuthenticationPrincipal String userId
  ) {
    String userId = "testuser"; // 테스트용 유저아이디
    return boardService.deleteBoardComment(commentSequence, userId);
  }
}
