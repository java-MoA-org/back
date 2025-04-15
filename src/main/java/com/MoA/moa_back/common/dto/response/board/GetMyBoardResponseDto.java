package com.MoA.moa_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.vo.BoardVo;
import com.MoA.moa_back.repository.BoardCommentRepository;
import com.MoA.moa_back.repository.BoardLikeRepository;

import lombok.Getter;

@Getter
public class GetMyBoardResponseDto extends ResponseDto {
    
  private List<BoardVo> boards;
  BoardLikeRepository likeRepository;
  BoardCommentRepository commentRepository;

  private GetMyBoardResponseDto(List<BoardEntity> boardEntities) {
    this.boards = BoardVo.getList(boardEntities, likeRepository, commentRepository);
  }

  public static ResponseEntity<GetMyBoardResponseDto> success(List<BoardEntity> boardEntities) {
    GetMyBoardResponseDto body = new GetMyBoardResponseDto(boardEntities);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

}
