package com.MoA.moa_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.daily.PatchDailyRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyCommentRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.daily.GetMyDailyResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.DailyCommentEntity;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.repository.BoardCommentRepository;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.MoA.moa_back.repository.BoardRepository;
import com.MoA.moa_back.repository.DailyCommentRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;
import com.MoA.moa_back.repository.DailyRepository;
import com.MoA.moa_back.service.DailyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyServiceImplement implements DailyService {

  private final DailyRepository dailyRepository;
  private final DailyLikeRepository dailyLikeRepository;
  private final DailyCommentRepository dailyCommentRepository;

  // method: 일상 게시글 작성 //
  @Override
  public ResponseEntity<ResponseDto> postDailyBoard(PostDailyRequestDto dto, String userId) {

    try {
      DailyEntity dailyEntity = new DailyEntity(dto, userId);
      dailyRepository.save(dailyEntity);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.CREATED);
  }

  // method: 나의 일상 게시글 목록 조회 //
  @Override
  public ResponseEntity<? super GetMyDailyResponseDto> getMyDaily(String userId) {

  }

  // method: 일상 게시판 일상 게시글 목록 조회 //
  @Override
  public ResponseEntity<? extends ResponseDto> getDailyBoardList(Integer pageNumber, Integer pageSize) {

  }

  // method: 일상 게시글 상세 조회 + 조회수 증가 + 댓글 포함 //
  @Override
  public ResponseEntity<ResponseDto> getDailyBoardDetail(Integer boardSequence) {

  }

  // method: 일상 게시글 수정 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> patchDailyBoard(PatchDailyRequestDto dto, Integer dailySequence, String userId) {

  }

  // method: 일상 게시글 삭제 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> deleteDailyBoard(Integer dailySequence, String userId) {

  }

  // method: 일상 게시글에 좋아요를 누르거나 취소 //
  @Override
  public ResponseEntity<ResponseDto> putDailyBoardLikeCount(Integer dailySequence, String userId) {

  }

  // method: 일상 게시글에 좋아요 목록 조회 //
  @Override
  public ResponseEntity<ResponseDto> getDailyBoardLikedUsers(Integer dailySequence) {

  }

  // method: 일상 게시글에 댓글 작성 //
  @Override
  public ResponseEntity<ResponseDto> postDailyBoardComment(PostDailyCommentRequestDto dto, Integer dailySequence, String userId) {

  }

  // method: 일상 게시글에 댓글 삭제 (글작성자, 댓글작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> deleteDailyComment(Integer commentSequence, String userId) {

    try {
      // 1. 댓글 엔티티 조회 //
      DailyCommentEntity commentEntity = dailyCommentRepository.findById(commentSequence).orElse(null);
      if (commentEntity == null)
        return ResponseDto.noExistComment();
  
      // 2. 게시글 작성자 조회 //
      DailyEntity dailyEntity = dailyRepository.findById(commentEntity.getDailySequence()).orElse(null);
      if (dailyEntity == null)
        return ResponseDto.noExistDaily();
  
      // 3. 댓글 작성자 또는 게시글 작성자가 아니면 권한 없음 //
      boolean isCommentWriter = commentEntity.getUserId().equals(userId);
      boolean isPostWriter = dailyEntity.getUserId().equals(userId);
  
      if (!isCommentWriter && !isPostWriter)
        return ResponseDto.noPermission();
  
      // 4. 삭제 수행 //
      dailyCommentRepository.deleteByCommentSequence(commentEntity.getCommentSequence());
  
      return ResponseDto.success(HttpStatus.OK);
  
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }
  
}
