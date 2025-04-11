package com.MoA.moa_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
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

  // API: 게시글 조회 //
  @GetMapping("/{boardSequence}")
  public ResponseEntity<? super GetBoardResponseDto> getBoard(
    @PathVariable("boardSequence") Integer boardSequence
  ) {
    ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardSequence);
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

}
