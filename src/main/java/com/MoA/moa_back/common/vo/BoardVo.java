package com.MoA.moa_back.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.repository.BoardCommentRepository;
import com.MoA.moa_back.repository.BoardLikeRepository;

import lombok.Getter;

@Getter
public class BoardVo {
  private Integer boardSequence;
  private String creationDate;
  private String title;
  private String tag;
  private int likeCount;
  private int commentCount;
  private String previewContent;
  private int views;

  private BoardVo(BoardEntity boardEntity, int likeCount, int commentCount, int views) {
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

  public static List<BoardVo> getList(List<BoardEntity> boardEntities, BoardLikeRepository likeRepository, BoardCommentRepository commentRepository) {
    List<BoardVo> list = new ArrayList<>();

    for (BoardEntity boardEntity : boardEntities) {
      int likeCount = likeRepository.countByBoardSequence(boardEntity.getBoardSequence());
      int commentCount = commentRepository.countByBoardSequence(boardEntity.getBoardSequence());
      int views = boardEntity.getViews();
      list.add(new BoardVo(boardEntity, likeCount, commentCount, views));
    }

    return list;
  }
}

