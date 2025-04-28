package com.MoA.moa_back.common.dto.response.daily;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetDailyListResponseDto extends ResponseDto {

  private List<DailySummaryResponseDto> dailyList;
  private Integer totalPages;
  private long totalElements;
  private int currentPage;
  private int currentSection;
  private int totalSection;
  private List<Integer> pageList;

  public GetDailyListResponseDto(List<DailySummaryResponseDto> dailyList, Integer totalPages, long totalElements) {
    this.dailyList = dailyList;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.currentPage = 0;
    this.currentSection = 0;
    this.totalSection = 0;
    this.pageList = null;
  }

  public static ResponseEntity<GetDailyListResponseDto> success(
    List<DailySummaryResponseDto> boardList,
    int totalPages,
    long totalElements,
    int currentPage,
    int currentSection,
    int totalSection,
    List<Integer> pageList
  ) {
    GetDailyListResponseDto body = new GetDailyListResponseDto(
      boardList, totalPages, totalElements,
      currentPage, currentSection, totalSection, pageList
    );
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

}
