package com.MoA.moa_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetBoardListResponseDto extends ResponseDto {
  private List<BoardSummaryResponseDto> boardList;
  private Integer totalPages;

  public static ResponseEntity<GetBoardListResponseDto> success(List<BoardSummaryResponseDto> boardList, int totalPages) {
    GetBoardListResponseDto body = new GetBoardListResponseDto(boardList, totalPages);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
  
}
