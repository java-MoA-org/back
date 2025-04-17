package com.MoA.moa_back.common.dto.response.home;

import java.util.List;

import com.MoA.moa_back.common.vo.summary.BoardSummaryVO;
import com.MoA.moa_back.common.vo.summary.DailySummaryVO;
import com.MoA.moa_back.common.vo.summary.UsedTradeSummaryVO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeSummaryResponseDto {

    // 인기 게시판 글 리스트
    private List<BoardSummaryVO> popularBoardList;

    // 인기 일상 글 리스트
    private List<DailySummaryVO> popularDailyList;

    // 최신 게시판 글 리스트
    private List<BoardSummaryVO> recentBoardList;

    // 최신 일상 글 리스트
    private List<DailySummaryVO> recentDailyList;

    // 최신 중고거래 글 리스트 (대표 이미지 포함)
    private List<UsedTradeSummaryVO> recentUsedTradeList;
}