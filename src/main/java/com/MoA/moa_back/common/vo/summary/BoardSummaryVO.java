package com.MoA.moa_back.common.vo.summary;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.repository.BoardLikeRepository;

import lombok.Getter;

@Getter
public class BoardSummaryVO {
  private Integer boardSequence;
  private String title;
  private Integer views;
  private int likeCount;
  private String creationDate;

  private BoardSummaryVO(BoardEntity boardEntity, int likeCount){
    this.boardSequence = boardEntity.getBoardSequence();
    this.title = boardEntity.getTitle();
    this.views = boardEntity.getViews();
    this.likeCount = likeCount;
    this.creationDate = boardEntity.getCreationDate();
  }

  public static List<BoardSummaryVO> getList(List<BoardEntity> boardEntities, BoardLikeRepository likeRepository){
    List<BoardSummaryVO> list = new ArrayList<>();

    for(BoardEntity boardEntity : boardEntities){
      int likeCount = likeRepository.countByBoardSequence(boardEntity.getBoardSequence());
      list.add(new BoardSummaryVO(boardEntity, likeCount));
    }
    return list;
  }
}
