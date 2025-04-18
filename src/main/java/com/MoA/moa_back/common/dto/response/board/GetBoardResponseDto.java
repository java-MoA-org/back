package com.MoA.moa_back.common.dto.response.board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.enums.BoardTagType;
import com.MoA.moa_back.common.vo.BoardCommentVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetBoardResponseDto extends ResponseDto {
  private Integer boardSequence;
  private String title;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  
  private BoardTagType tag;
  private Integer views;
  private Integer likeCount;
  private List<BoardCommentVO> comments;

  public static GetBoardResponseDto of(
    BoardEntity board,
    int likeCount,
    List<BoardCommentVO> commentList
  ) {
    return new GetBoardResponseDto(
      board.getBoardSequence(),
      board.getTitle(),
      board.getContent(),
      board.getCreationDate(),
      board.getTag(),
      board.getViews(),
      likeCount,
      commentList
    );
  }

  public static ResponseEntity<GetBoardResponseDto> success(
    BoardEntity board,
    int likeCount,
    List<BoardCommentVO> commentList
  ) {
    GetBoardResponseDto body = GetBoardResponseDto.of(board, likeCount, commentList);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
  
}
