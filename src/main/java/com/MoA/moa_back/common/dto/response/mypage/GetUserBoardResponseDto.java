package com.MoA.moa_back.common.dto.response.mypage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.MoA.moa_back.common.vo.BoardSummaryVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserBoardResponseDto {
  private List<BoardSummaryVO> boards;

      public static ResponseEntity<GetUserBoardResponseDto> success(List<BoardSummaryVO> boards) {
        GetUserBoardResponseDto dto = new GetUserBoardResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }
}
