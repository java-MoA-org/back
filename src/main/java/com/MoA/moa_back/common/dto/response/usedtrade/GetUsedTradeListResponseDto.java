package com.MoA.moa_back.common.dto.response.usedtrade;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUsedTradeListResponseDto extends ResponseDto {

  private List<UsedTradeSummaryResponseDto> usedTradeList;
  private Integer totalPages;
  private long totalElements;
  private int currentPage;
  private int currentSection;
  private int totalSection;
  private List<Integer> pageList;

  // 기존 생성자 수정: page 관련 필드도 포함하여 생성
  public GetUsedTradeListResponseDto(List<UsedTradeSummaryResponseDto> usedTradeList, Integer totalPages, long totalElements) {
    this.usedTradeList = usedTradeList;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.currentPage = 0;
    this.currentSection = 0;
    this.totalSection = 0;
    this.pageList = null;
  }

  // 성공 응답 반환 메서드 수정
  public static ResponseEntity<GetUsedTradeListResponseDto> success(
    List<UsedTradeSummaryResponseDto> usedTradeList,
    int totalPages,
    long totalElements,
    int currentPage,
    int currentSection,
    int totalSection,
    List<Integer> pageList
  ) {
    GetUsedTradeListResponseDto body = new GetUsedTradeListResponseDto(
      usedTradeList, totalPages, totalElements,
      currentPage, currentSection, totalSection, pageList
    );
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
