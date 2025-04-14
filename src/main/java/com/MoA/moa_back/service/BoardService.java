package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardCommentRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;

public interface BoardService {

  // method: 게시글 작성 //
  ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId);
  // method: 게시글 상세 조회 및 조회수 증가 //
  ResponseEntity<ResponseDto> getBoardDetail(Integer boardSequence);
  // method: 게시판(태그) 별 게시글 목록 조회 (페이징)//
  ResponseEntity<? extends ResponseDto> getBoardListByBoardTag(String tag, Integer pageNumber, Integer pageSize);
  // method: 게시글 수정 //
  ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardSequence, String userId);
  // method: 게시글 삭제 //
  ResponseEntity<ResponseDto> deleteBoard(Integer boardSequence, String userId);

  // method: 특정 게시글 좋아요 누르거나 취소 //
  ResponseEntity<ResponseDto> putBoardLikeCount(Integer boardSequence, String userId);

  // method: 특정 게시글 댓글 작성 //
  ResponseEntity<ResponseDto> postBoardComment(PostBoardCommentRequestDto dto, Integer boardSequence, String userId);

}
