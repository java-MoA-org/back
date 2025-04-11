package com.MoA.moa_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.repository.BoardRepository;
import com.MoA.moa_back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

  private final BoardRepository boardRepository;

  // method: 게시글 등록 //
  @Override
  public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId) {

    try {

      BoardEntity boardEntity = new BoardEntity(dto, userId);
      boardRepository.save(boardEntity);

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.CREATED);

  }

  // method: 게시글 상세 조회 //
  @Override
  public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardSequence) {

    BoardEntity boardEntity = null;
    
    try {

      boardEntity = boardRepository.findByBoardSequence(boardSequence);

      if (boardEntity == null) return ResponseDto.noExistBoard();
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetBoardResponseDto.success(boardEntity);

  }

  // method: 게시글 수정 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardSequence, String userId) {
    
    try {

      BoardEntity boardEntity = boardRepository.findByBoardSequence(boardSequence);
      if (boardEntity == null) return ResponseDto.noExistBoard();

      String writerId = boardEntity.getUserId();
      boolean isWriter = writerId.equals(userId);
      if (!isWriter) return ResponseDto.noPermission();

      boardEntity.patch(dto);
      boardRepository.save(boardEntity);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);

  }

  // method: 게시글 삭제 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> deleteBoard(Integer boardSequence, String userId) {

    try {
      
      BoardEntity boardEntity = boardRepository.findByBoardSequence(boardSequence);
      if (boardEntity == null) return ResponseDto.noExistBoard();

      String writerId = boardEntity.getUserId();
      boolean isWriter = writerId.equals(userId);
      if (!isWriter) return ResponseDto.noPermission();

      // 좋아요도 같이 삭제 ( 후에 구현 )//
      // BoardLikeRepository.deleteByboardSequence(boardEntity);
      // 댓글도 같이 삭제 ( 후에 구현 ) //
      // BoardCommentRepository.deleteByboardSequence(boardEntity);
      boardRepository.delete(boardEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);

  }

}
