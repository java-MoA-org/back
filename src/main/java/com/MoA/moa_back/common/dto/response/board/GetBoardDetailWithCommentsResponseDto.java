package com.MoA.moa_back.common.dto.response.board;

import java.util.List;

import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.TagType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetBoardDetailWithCommentsResponseDto {
  private Integer boardSequence;
  private String title;
  private String content;
  private String creationDate;
  private TagType tag;
  private Integer views;
  private Integer likeCount;
  private List<BoardCommentResponseDto> comments;

  public static GetBoardDetailWithCommentsResponseDto of(
    BoardEntity board,
    int likeCount,
    List<BoardCommentResponseDto> commentList
  ) {
    return new GetBoardDetailWithCommentsResponseDto(
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
}
