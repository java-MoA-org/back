package com.MoA.moa_back.common.dto.response.daily;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.board.BoardCommentResponseDto;
import com.MoA.moa_back.common.dto.response.board.GetBoardResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.DailyEntity;

import lombok.Getter;

@Getter
public class GetDailyResponseDto extends ResponseDto {
  
  private String creationDate;
  private String title;
  private String content;
  private Integer views;
  private Integer likeCount;
  private List<String> images;
  private String writerId;
  private String profileImage;
  private List<DailyCommentResponseDto> commentList;

  public GetDailyResponseDto(DailyEntity dailyEnttity, int likeCount, List<DailyCommentResponseDto> commentList, String profileImage) {
    this.creationDate = dailyEnttity.getCreationDate();
    this.title = dailyEnttity.getTitle();
    this.content = dailyEnttity.getContent();
    this.views = dailyEnttity.getViews();
    this.likeCount = likeCount;
    this.images = dailyEnttity.getImages();
    this.writerId = dailyEnttity.getUserId();
    this.profileImage = profileImage;
    this.commentList = commentList;
  }

  public static ResponseEntity<GetBoardResponseDto> success(BoardEntity boardEntity, int likeCount, List<BoardCommentResponseDto> commentList) {
    GetBoardResponseDto dto = new GetBoardResponseDto(boardEntity, likeCount, commentList);
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }

}
