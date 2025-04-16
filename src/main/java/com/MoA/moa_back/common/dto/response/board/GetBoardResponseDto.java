package com.MoA.moa_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.enums.BoardTagType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetBoardResponseDto extends ResponseDto {
  private Integer boardSequence;
  private String title;
  private String content;
  private String creationDate;
  private BoardTagType tag;
  private Integer views;
  private Integer likeCount;
  private List<BoardCommentResponseDto> comments;

  public static GetBoardResponseDto of(
    BoardEntity board,
    int likeCount,
    List<BoardCommentResponseDto> commentList
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
    List<BoardCommentResponseDto> commentList
  ) {
    GetBoardResponseDto body = GetBoardResponseDto.of(board, likeCount, commentList);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
  
}
