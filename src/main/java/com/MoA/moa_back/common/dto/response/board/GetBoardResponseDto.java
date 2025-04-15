package com.MoA.moa_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.TagType;

import lombok.Getter;

@Getter
public class GetBoardResponseDto extends ResponseDto {

  private String creationDate;
  private String title;
  private String content;
  private TagType tag;
  private Integer views;
  private Integer likeCount;
  private List<String> images;
  private String writerId;
  private List<BoardCommentResponseDto> commentList;

  public GetBoardResponseDto(BoardEntity boardEntity, int likeCount, List<BoardCommentResponseDto> commentList) {
    this.creationDate = boardEntity.getCreationDate();
    this.title = boardEntity.getTitle();
    this.content = boardEntity.getContent();
    this.tag = boardEntity.getTag();
    this.views = boardEntity.getViews();
    this.likeCount = likeCount;
    this.images = boardEntity.getImages();
    this.writerId = boardEntity.getUserId();
    this.commentList = commentList;
  }
  

  public static ResponseEntity<GetBoardResponseDto> success(BoardEntity boardEntity, int likeCount, List<BoardCommentResponseDto> commentList) {
    GetBoardResponseDto dto = new GetBoardResponseDto(boardEntity, likeCount, commentList);
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }
}
