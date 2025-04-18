package com.MoA.moa_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardCommentRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.board.BoardSummaryResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardCommentResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardListResponseDto;
import com.MoA.moa_back.common.entity.BoardCommentEntity;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.BoardLikeEntity;
import com.MoA.moa_back.common.enums.BoardTagType;
import com.MoA.moa_back.common.util.PageUtil;
import com.MoA.moa_back.common.vo.BoardCommentVO;
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
      return ResponseDto.success(HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 게시판 게시글 목록 조회 최신순 좋아요순 정렬 가능 (태그기준, 페이징) //
  @Override
  public ResponseEntity<? super GetBoardListResponseDto> getBoardListByBoardTag(String tag, Integer pageNumber, String sortOption) {
    try {
      int pageSize = 10;

      Sort sort;
      switch (sortOption.toUpperCase()) {
        case "LIKES":
          sort = Sort.by(Sort.Order.desc("boardSequence"));
          break;
        case "VIEWS":
          sort = Sort.by(Sort.Order.desc("views"));
          break;
        default:
          sort = Sort.by(Sort.Order.desc("creationDate"), Sort.Order.desc("boardSequence"));
          break;
      }

      Pageable pageable = PageUtil.createPageable(pageNumber, pageSize, sort);

      BoardTagType boardTagType = null;
      if (!"ALL".equalsIgnoreCase(tag)) {
        try {
          boardTagType = BoardTagType.valueOf(tag);
        } catch (IllegalArgumentException e) {
          return ResponseDto.invalidTag();
        }
      }

      Page<BoardEntity> page = (boardTagType == null)
        ? boardRepository.findAll(pageable)
        : boardRepository.findByTag(boardTagType, pageable);

      if (PageUtil.isInvalidPageIndex(pageable.getPageNumber(), page.getTotalPages())) {
        return ResponseDto.invalidPageNumber();
      }

      List<BoardSummaryResponseDto> list = page.stream()
        .map(entity -> {
          int likeCount = boardLikeRepository.countByBoardSequence(entity.getBoardSequence());
          int commentCount = boardCommentRepository.countByBoardSequence(entity.getBoardSequence());

          return new BoardSummaryResponseDto(
            entity.getBoardSequence(),
            entity.getTitle(),
            entity.getContent(),
            entity.getCreationDate(),
            entity.getTag(),
            entity.getViews(),
            entity.getUserId(),
            likeCount,
            commentCount
          );
        })
        .toList();

      if (sortOption.equalsIgnoreCase("LIKES")) {
        list = list.stream()
          .sorted((a, b) -> b.getLikeCount() - a.getLikeCount())
          .toList();
      }

      return ResponseEntity.status(HttpStatus.OK).body(new GetBoardListResponseDto(list, page.getTotalPages()));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 게시글 상세 조회 + 조회수 증가 //
  @Override
  public ResponseEntity<? super GetBoardResponseDto> getBoardDetail(Integer boardSequence) {
    try {
      BoardEntity boardEntity = boardRepository.findById(boardSequence).orElse(null);
      if (boardEntity == null) return ResponseDto.noExistBoard();

      boardEntity.setViews(boardEntity.getViews() + 1);
      boardRepository.save(boardEntity);

      int likeCount = boardLikeRepository.countByBoardSequence(boardSequence);

      List<BoardCommentEntity> commentEntities = boardCommentRepository.findByBoardSequenceOrderByCreationDateDesc(boardSequence);
      List<BoardCommentVO> commentList = commentEntities.stream()
        .map(BoardCommentVO::new)
        .toList();

      return GetBoardResponseDto.success(boardEntity, likeCount, commentList);
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

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
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

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 게시글 검색 (태그별로 가능) //
  @Override
  public ResponseEntity<? super GetBoardListResponseDto> searchBoardList(String tag, String keyword, Integer pageNumber) {
    try {
      int pageSize = 10;
      Sort sort = Sort.by("boardSequence").descending();
      Pageable pageable = PageUtil.createPageable(pageNumber, pageSize, sort);

      BoardTagType tagType = null;
      if (!"ALL".equalsIgnoreCase(tag)) {
        try {
          tagType = BoardTagType.valueOf(tag);
        } catch (IllegalArgumentException e) {
          return ResponseDto.invalidTag();
        }
      }

      Page<BoardEntity> boardPage = (tagType == null)
        ? boardRepository.findByTitleContaining(keyword, pageable)
        : boardRepository.findByTagAndTitleContaining(tagType, keyword, pageable);

      if (PageUtil.isInvalidPageIndex(pageable.getPageNumber(), boardPage.getTotalPages())) {
        return ResponseDto.invalidPageNumber();
      }

      List<BoardSummaryResponseDto> boardList = boardPage.stream()
        .map(entity -> {
          int likeCount = boardLikeRepository.countByBoardSequence(entity.getBoardSequence());
          int commentCount = boardCommentRepository.countByBoardSequence(entity.getBoardSequence());

          return new BoardSummaryResponseDto(
            entity.getBoardSequence(),
            entity.getTitle(),
            entity.getContent(),
            entity.getCreationDate(),
            entity.getTag(),
            entity.getViews(),
            entity.getUserId(),
            likeCount,
            commentCount
          );
        }).toList();

      return ResponseEntity.ok(new GetBoardListResponseDto(boardList, boardPage.getTotalPages()));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
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

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // method: 게시글에 댓글 작성 //
  @Override
  public ResponseEntity<ResponseDto> postBoardComment(PostBoardCommentRequestDto dto, Integer boardSequence, String userId) {
    try {
      boolean existBoard = boardRepository.existsByBoardSequence(boardSequence);
      if (!existBoard) return ResponseDto.noExistBoard();

      BoardCommentEntity boardCommentEntity = new BoardCommentEntity(dto, boardSequence, userId);
      boardCommentRepository.save(boardCommentEntity);

      return ResponseDto.success(HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }
  
  // method: 특정 게시글 댓글 불러오기 //
  @Override
  public ResponseEntity<? super GetBoardCommentResponseDto> getCommentsByBoardSequence(Integer boardSequence) {

    List<BoardCommentEntity> commentEntities = new ArrayList<>();

    try {

      commentEntities = boardCommentRepository.findByBoardSequenceOrderByCreationDateDesc(boardSequence);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetBoardCommentResponseDto.success(commentEntities);
  }

  // method: 게시글에 댓글 삭제 (글작성자, 댓글작성자만 가능) //
  @Override
  public ResponseEntity<ResponseDto> deleteBoardComment(Integer commentSequence, String userId) {
    try {
      BoardCommentEntity commentEntity = boardCommentRepository.findById(commentSequence).orElse(null);
      if (commentEntity == null) return ResponseDto.noExistComment();

      BoardEntity boardEntity = boardRepository.findById(commentEntity.getBoardSequence()).orElse(null);
      if (boardEntity == null) return ResponseDto.noExistBoard();

      boolean isCommentWriter = commentEntity.getUserId().equals(userId);
      boolean isPostWriter = boardEntity.getUserId().equals(userId);
      if (!isCommentWriter && !isPostWriter) return ResponseDto.noPermission();

      boardCommentRepository.deleteByCommentSequence(commentEntity.getCommentSequence());

      return ResponseDto.success(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
  }
}
