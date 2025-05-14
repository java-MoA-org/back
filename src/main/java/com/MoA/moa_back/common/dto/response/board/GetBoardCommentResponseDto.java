package com.MoA.moa_back.common.dto.response.board;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.BoardCommentEntity;
import com.MoA.moa_back.common.vo.BoardCommentVO;

import lombok.Getter;

@Getter
public class GetBoardCommentResponseDto extends ResponseDto {

  private List<BoardCommentVO> comments;

  private GetBoardCommentResponseDto(List<BoardCommentEntity> commentEntities, String postWriterId) {
    this.comments = generateAnonymousComments(commentEntities, postWriterId);
  }

  public static ResponseEntity<GetBoardCommentResponseDto> success(List<BoardCommentEntity> commentEntities, String postWriterId) {
    GetBoardCommentResponseDto body = new GetBoardCommentResponseDto(commentEntities, postWriterId);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

  private List<BoardCommentVO> generateAnonymousComments(List<BoardCommentEntity> commentEntities, String postWriterId) {
    Map<String, String> anonymousMap = new HashMap<>();
    int anonymousCounter = 1;

    List<BoardCommentVO> anonymousCommentList = new ArrayList<>();

    for (BoardCommentEntity comment : commentEntities) {
      String writerId = comment.getUserId();

      if (writerId.equals(postWriterId)) {
        anonymousMap.put(writerId, "글쓴이");
      } else if (!anonymousMap.containsKey(writerId)) {
        anonymousMap.put(writerId, "익명 " + anonymousCounter++);
      }

      String displayName = anonymousMap.get(writerId);
      BoardCommentVO vo = new BoardCommentVO(comment, displayName);
      anonymousCommentList.add(vo);
    }

    return anonymousCommentList;
  }
}
