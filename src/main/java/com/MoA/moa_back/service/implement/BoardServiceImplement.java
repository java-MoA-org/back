package com.MoA.moa_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardCommentRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.board.BoardCommentResponseDto;
import com.MoA.moa_back.common.dto.response.board.BoardSummaryResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardListResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardResponseDto;
import com.MoA.moa_back.common.entity.BoardCommentEntity;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.BoardLikeEntity;
import com.MoA.moa_back.common.entity.TagType;
import com.MoA.moa_back.repository.BoardCommentRepository;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.MoA.moa_back.repository.BoardRepository;
import com.MoA.moa_back.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

  private final BoardRepository boardRepository;
  private final BoardLikeRepository boardLikeRepository;
  private final BoardCommentRepository boardCommentRepository;

  // method: 게시글 등록 //
  @Override
  public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId) {
    try {
      BoardEntity boardEntity = new BoardEntity(dto, userId);
      boardRepository.save(boardEntity);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.CREATED);
  }


  // method: 게시판(태그) 별 게시글 목록 조회 //
  @Override
  public ResponseEntity<? extends ResponseDto> getBoardListByBoardTag(String tag, Integer pageNumber, Integer pageSize) {
    TagType tagType;
    try {
      tagType = TagType.valueOf(tag);
    } catch (IllegalArgumentException e) {
      return ResponseDto.invalidTag();
    }

    try {
      pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
      int pageIndex = pageNumber - 1;

      if (pageIndex < 0) return ResponseDto.invalidPageNumber();

      Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("boardSequence").descending());
      Page<BoardEntity> boardPage = boardRepository.findByTag(tagType, pageable);

      if (pageIndex >= boardPage.getTotalPages()) return ResponseDto.invalidPageNumber();

      List<BoardSummaryResponseDto> boardList = boardPage.stream()
        .map(entity -> {
          int likeCount = boardLikeRepository.countByBoardSequence(entity.getBoardSequence());
          return new BoardSummaryResponseDto(
            entity.getBoardSequence(),
            entity.getTitle(),
            entity.getContent(),
            entity.getCreationDate(),
            entity.getTag(),
            entity.getViews(),
            likeCount
          );
        })
        .toList();

      GetBoardListResponseDto responseBody = new GetBoardListResponseDto(boardList, boardPage.getTotalPages());
      return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 게시글 상세 조회 + 조회수 증가 //
  @Override
  public ResponseEntity<ResponseDto> getBoardDetail(Integer boardSequence) {
    try {
      BoardEntity boardEntity = boardRepository.findById(boardSequence).orElse(null);
      if (boardEntity == null) return ResponseDto.noExistBoard();  

      boardEntity.setViews(boardEntity.getViews() + 1);
      boardRepository.save(boardEntity);

      int likeCount = boardLikeRepository.countByBoardSequence(boardSequence);

      List<BoardCommentEntity> commentEntities = boardCommentRepository.findByBoardSequenceOrderByCreationDateDesc(boardSequence);
      List<BoardCommentResponseDto> commentList = commentEntities.stream()
      .map(BoardCommentResponseDto::new)
      .toList();

      GetBoardResponseDto responseDto = new GetBoardResponseDto(boardEntity, likeCount, commentList);
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError(); 
    }
  }

  // method: 게시글 수정 (작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardSequence, String userId) {
    try {
      BoardEntity boardEntity = boardRepository.findByBoardSequence(boardSequence);
      if (boardEntity == null) return ResponseDto.noExistBoard();

      String writerId = boardEntity.getUserId();
      if (!writerId.equals(userId)) return ResponseDto.noPermission();

      boardEntity.patch(dto);
      boardRepository.save(boardEntity);

    } catch (Exception e) {
      e.printStackTrace();
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
      if (!writerId.equals(userId)) return ResponseDto.noPermission();

      boardLikeRepository.deleteByBoardSequence(boardSequence);
      boardCommentRepository.deleteByBoardSequence(boardSequence);
      boardRepository.delete(boardEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);
  }

  // method: 게시글에 좋아요를 누르거나 취소 //
  @Override
  public ResponseEntity<ResponseDto> putBoardLikeCount(Integer boardSequence, String userId) {
    try {
      boolean existBoard = boardRepository.existsByBoardSequence(boardSequence);
      if (!existBoard) return ResponseDto.noExistBoard();

      boolean hasLiked = boardLikeRepository.existsByBoardSequenceAndUserId(boardSequence, userId);
      if (!hasLiked) {
        BoardLikeEntity boardLikeEntity = new BoardLikeEntity();
        boardLikeEntity.setBoardSequence(boardSequence);
        boardLikeEntity.setUserId(userId);
        boardLikeRepository.save(boardLikeEntity);
      } else {
        boardLikeRepository.deleteByBoardSequenceAndUserId(boardSequence, userId);
      }

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);
  }

  // method: 게시글에 댓글 작성 //
  @Override
  public ResponseEntity<ResponseDto> postBoardComment(PostBoardCommentRequestDto dto, Integer boardSequence, String userId) {
    try {
      boolean existBoard = boardRepository.existsByBoardSequence(boardSequence);
      if (!existBoard) return ResponseDto.noExistBoard();

      BoardCommentEntity boardCommentEntity = new BoardCommentEntity(dto, boardSequence, userId);
      boardCommentRepository.save(boardCommentEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.CREATED);
  }

  // method: 게시글에 댓글 삭제 (글작성자, 댓글작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> deleteBoardComment(Integer commentSequence, String userId) {

    try {
      // 1. 댓글 엔티티 조회 //
      BoardCommentEntity commentEntity = boardCommentRepository.findById(commentSequence).orElse(null);
      if (commentEntity == null)
        return ResponseDto.noExistComment();
  
      // 2. 게시글 작성자 조회 //
      BoardEntity boardEntity = boardRepository.findById(commentEntity.getBoardSequence()).orElse(null);
      if (boardEntity == null)
        return ResponseDto.noExistBoard();
  
      // 3. 댓글 작성자 또는 게시글 작성자가 아니면 권한 없음 //
      boolean isCommentWriter = commentEntity.getUserId().equals(userId);
      boolean isPostWriter = boardEntity.getUserId().equals(userId);
  
      if (!isCommentWriter && !isPostWriter)
        return ResponseDto.noPermission();
  
      // 4. 삭제 수행 //
      boardCommentRepository.deleteByCommentSequence(commentEntity.getCommentSequence());
  
      return ResponseDto.success(HttpStatus.OK);
  
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

}

