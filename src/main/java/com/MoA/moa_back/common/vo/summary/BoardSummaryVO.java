package com.MoA.moa_back.common.vo.summary;

import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.repository.BoardCommentRepository;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.MoA.moa_back.repository.UserRepository;

import lombok.Getter;

@Getter
public class BoardSummaryVO {
  private Integer boardSequence;
  private String title;
  private Integer views;
  private int likeCount;
  private String creationDate;

    // 메인홈 전용 추가 필드
    private String userNickname;
    private int commentCount;

  private BoardSummaryVO(BoardEntity boardEntity, int likeCount){
    this.boardSequence = boardEntity.getBoardSequence();
    this.title = boardEntity.getTitle();
    this.views = boardEntity.getViews();
    this.likeCount = likeCount;
    this.creationDate = boardEntity.getCreationDate();
  }

   // 메인 홈 전용 생성자
   private BoardSummaryVO(BoardEntity boardEntity, int likeCount, int commentCount, String userNickname) {
    this.boardSequence = boardEntity.getBoardSequence();
    this.title = boardEntity.getTitle();
    this.views = boardEntity.getViews();
    this.likeCount = likeCount;
    this.creationDate = boardEntity.getCreationDate();
    this.commentCount = commentCount;
    this.userNickname = userNickname;
  }

  public static List<BoardSummaryVO> getList(List<BoardEntity> boardEntities, BoardLikeRepository likeRepository){
    List<BoardSummaryVO> list = new ArrayList<>();

    for(BoardEntity boardEntity : boardEntities){
      int likeCount = likeRepository.countByBoardSequence(boardEntity.getBoardSequence());
      list.add(new BoardSummaryVO(boardEntity, likeCount));
    }
    return list;
  }
  // 메인 홈 전용 리스트 생성
  public static List<BoardSummaryVO> getList(
    List<BoardEntity> boardEntities,
    BoardLikeRepository likeRepository,
    BoardCommentRepository commentRepository,
    UserRepository userRepository
  ) {
    List<BoardSummaryVO> list = new ArrayList<>();

    for (BoardEntity boardEntity : boardEntities) {
      int likeCount = likeRepository.countByBoardSequence(boardEntity.getBoardSequence());
      int commentCount = commentRepository.countByBoardSequence(boardEntity.getBoardSequence());
      String nickname = userRepository.findById(boardEntity.getUserId())
                          .map(UserEntity::getUserNickname)
                          .orElse("탈퇴한 사용자");

      list.add(new BoardSummaryVO(boardEntity, likeCount, commentCount, nickname));
    }
    return list;
  }
}
