package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardResponseDto;

public interface BoardService {

  // method: 게시글 작성 //
  ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId);

  // method: 게시글 상세보기 //
  ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardSequence);

  // method: 게시글 수정 //
  ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardSequence, String userId);

  // method: 게시글 삭제 //
  ResponseEntity<ResponseDto> deleteBoard(Integer boardSequence, String userId);

  // method: 특정 게시글에 눌린 좋아요 수 조회 //
  // ResponseEntity<? super GetBoardLikeCountResponseDto> getBoardLikeCount(Integer boardSequence);

}
