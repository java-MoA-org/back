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

  private List<UsedTradeSummaryResponseDto> dailyList;
  private Integer totalPages;

  public static ResponseEntity<GetUsedTradeListResponseDto> success(List<UsedTradeSummaryResponseDto> usedTradeList, int totalPages) {
    GetUsedTradeListResponseDto body = new GetUsedTradeListResponseDto(usedTradeList, totalPages);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

}
