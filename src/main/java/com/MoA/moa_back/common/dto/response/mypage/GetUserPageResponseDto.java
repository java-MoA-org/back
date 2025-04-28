package com.MoA.moa_back.common.dto.response.mypage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.vo.UserInterestVO;
import com.MoA.moa_back.common.vo.summary.BoardSummaryVO;
import com.MoA.moa_back.common.vo.summary.DailySummaryVO;
import com.MoA.moa_back.common.vo.summary.UsedTradeSummaryVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPageResponseDto extends ResponseDto {
  private List<BoardSummaryVO> boards;
  private List<DailySummaryVO> dailyBoards;
  private List<UsedTradeSummaryVO> tradeBoards;
  private UserInterestVO interests;
  private String userIntroduce;
  private String userProfileImage;


      public static ResponseEntity<GetUserPageResponseDto> success(List<BoardSummaryVO> boards, List<DailySummaryVO> dailyBoards, List<UsedTradeSummaryVO> tradeBoards, UserInterestVO interests, String userIntroduce, String userProfileImage) {
        GetUserPageResponseDto body = new GetUserPageResponseDto(boards, dailyBoards, tradeBoards, interests, userIntroduce, userProfileImage);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
