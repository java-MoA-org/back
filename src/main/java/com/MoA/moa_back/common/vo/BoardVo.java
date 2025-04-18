package com.MoA.moa_back.common.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.repository.BoardCommentRepository;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class BoardVO {
  private Integer boardSequence;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  
  private String title;
  private String tag;
  private int likeCount;
  private int commentCount;
  private String previewContent;
  private int views;

  private BoardVO(BoardEntity boardEntity, int likeCount, int commentCount, int views) {
    this.boardSequence = boardEntity.getBoardSequence();
    this.creationDate = boardEntity.getCreationDate();
    this.title = boardEntity.getTitle();
    this.tag = boardEntity.getTag().name();
    this.likeCount = likeCount;
    this.commentCount = commentCount;
    this.views = views;

    String content = boardEntity.getContent();
    this.previewContent = content.length() > 50 ? content.substring(0, 50) + "..." : content;
  }

  public static List<BoardVO> getList(List<BoardEntity> boardEntities, BoardLikeRepository likeRepository, BoardCommentRepository commentRepository) {
    List<BoardVO> list = new ArrayList<>();

    for (BoardEntity boardEntity : boardEntities) {
      int likeCount = likeRepository.countByBoardSequence(boardEntity.getBoardSequence());
      int commentCount = commentRepository.countByBoardSequence(boardEntity.getBoardSequence());
      int views = boardEntity.getViews();
      list.add(new BoardVO(boardEntity, likeCount, commentCount, views));
    }

    return list;
  }
}

