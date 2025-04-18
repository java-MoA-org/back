package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardCommentRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardCommentResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardListResponseDto;

public interface BoardService {

  // method: 게시글 작성 //
  ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId);
  // method: 게시판 게시글 목록 조회 최신순 좋아요순 정렬 기능 (태그기준, 페이징) //
  ResponseEntity<? super GetBoardListResponseDto> getBoardListByBoardTag(String tag, Integer pageNumber, String sortOption);
  // method: 게시글 상세 조회 + 조회수 증가 //
  ResponseEntity<? super GetBoardResponseDto> getBoardDetail(Integer boardSequence);
  // method: 게시글 수정 //
  ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardSequence, String userId);
  // method: 게시글 삭제 //
  ResponseEntity<ResponseDto> deleteBoard(Integer boardSequence, String userId);
  // method: 게시글 (태그)별 검색 // 
  ResponseEntity<? super GetBoardListResponseDto> searchBoardList(String tag, String keyword, Integer pageNumber);

  // method: 특정 게시글 좋아요 누르거나 취소 //
  ResponseEntity<ResponseDto> putBoardLikeCount(Integer boardSequence, String userId);

  // method: 특정 게시글 댓글 작성 //
  ResponseEntity<ResponseDto> postBoardComment(PostBoardCommentRequestDto dto, Integer boardSequence, String userId);
  // method: 특정 게시글 댓글 불러오기 //
  ResponseEntity<? super GetBoardCommentResponseDto> getCommentsByBoardSequence(Integer boardSequence);
  // method: 특정 게시글 댓글 삭제 (작성자만 가능) //
  ResponseEntity<ResponseDto> deleteBoardComment(Integer commentSequence, String userId);

}
