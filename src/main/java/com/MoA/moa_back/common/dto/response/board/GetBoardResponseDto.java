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
  
  private GetBoardResponseDto(BoardEntity boardEntity) {
    this.creationDate = boardEntity.getCreationDate();
    this.title = boardEntity.getTitle();
    this.content = boardEntity.getContent();
    this.tag = boardEntity.getTag();
    this.views = boardEntity.getViews();
    this.likeCount = 0;  
    this.images = boardEntity.getImages();  
    this.writerId = boardEntity.getUserId();
  }

  public static ResponseEntity<GetBoardResponseDto> success(BoardEntity boardEntity) {
    GetBoardResponseDto body = new GetBoardResponseDto(boardEntity);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
