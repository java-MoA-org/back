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

  public static ResponseEntity<GetDailyListResponseDto> success(List<DailySummaryResponseDto> dailyList, int totalPages) {
    GetDailyListResponseDto body = new GetDailyListResponseDto(dailyList, totalPages);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

}
