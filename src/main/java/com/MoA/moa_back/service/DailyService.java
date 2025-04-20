package com.MoA.moa_back.service;

import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.request.daily.PatchDailyRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyCommentRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetDailyResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetDailyCommentResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetDailyListResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetLikedUserListResponseDto;

public interface DailyService {
  
  // method: 일상 게시글 작성 //
  ResponseEntity<ResponseDto> postDailyBoard(PostDailyRequestDto dto, String userId);
  // method: 일상게시판 게시글 목록 조회 (페이징)//
  ResponseEntity<? super GetDailyListResponseDto> getDailyBoardList(Integer pageNumber, String sortOption);
  // method: 일상 게시글 상세 조회 및 조회수 증가 //
  ResponseEntity<? super GetDailyResponseDto> getDailyBoardDetail(Integer boardSequence);
  // method: 일상 게시글 수정 //
  ResponseEntity<ResponseDto> patchDailyBoard(PatchDailyRequestDto dto, Integer dailySequence, String userId);
  // method: 일상 게시글 삭제 //
  ResponseEntity<ResponseDto> deleteDailyBoard(Integer dailySequence, String userId);
  // method: 일상 게시글 검색 // 
  ResponseEntity<? super GetDailyListResponseDto> searchDailyBoardList(String keyword, Integer pageNumber);

  // method: 특정 게시글 좋아요 누르거나 취소 //
  ResponseEntity<ResponseDto> putDailyBoardLikeCount(Integer dailySequence, String userId);
  // method: 특정 게시글 좋아요 누른 유저 목록 조회 //
  ResponseEntity<? super GetLikedUserListResponseDto> getDailyBoardLikedUsers(Integer dailySequence);

  // method: 특정 게시글 댓글 작성 //
  ResponseEntity<ResponseDto> postDailyBoardComment(PostDailyCommentRequestDto dto, Integer dailySequence, String userId);
  // method: 특정 게시글 댓글 불러오기 //
  ResponseEntity<? super GetDailyCommentResponseDto> getCommentsByDailySequence(Integer dailySequence);
  // method: 특정 게시글 댓글 삭제 (작성자만 가능) //
  ResponseEntity<ResponseDto> deleteDailyComment(Integer commentSequence, String userId);

}
