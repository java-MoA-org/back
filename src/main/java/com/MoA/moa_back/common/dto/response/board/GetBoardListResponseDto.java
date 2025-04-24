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
  private long totalElements;
  private int currentPage;
  private int currentSection;
  private int totalSection;
  private List<Integer> pageList;

  public GetBoardListResponseDto(List<BoardSummaryResponseDto> boardList, Integer totalPages, long totalElements) {
    this.boardList = boardList;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.currentPage = 0;
    this.currentSection = 0;
    this.totalSection = 0;
    this.pageList = null;
  }

  public static ResponseEntity<GetBoardListResponseDto> success(
    List<BoardSummaryResponseDto> boardList,
    int totalPages,
    long totalElements,
    int currentPage,
    int currentSection,
    int totalSection,
    List<Integer> pageList
  ) {
    GetBoardListResponseDto body = new GetBoardListResponseDto(
      boardList, totalPages, totalElements,
      currentPage, currentSection, totalSection, pageList
    );
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}

