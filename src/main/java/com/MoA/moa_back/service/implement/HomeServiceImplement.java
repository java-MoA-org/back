package com.MoA.moa_back.service.implement;

import com.MoA.moa_back.common.dto.response.home.HomeSummaryResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.vo.summary.BoardSummaryVO;
import com.MoA.moa_back.common.vo.summary.DailySummaryVO;
import com.MoA.moa_back.common.vo.summary.UsedTradeSummaryVO;
import com.MoA.moa_back.repository.*;
import com.MoA.moa_back.service.HomeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImplement implements HomeService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardCommentRepository boardCommentRepository;

    private final DailyRepository dailyRepository;
    private final DailyLikeRepository dailyLikeRepository;
    private final DailyCommentRepository dailyCommentRepository;

    private final UsedTradeRepository usedTradeRepository;
    private final UsedTradeLikeRepository usedTradeLikeRepository;

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<HomeSummaryResponseDto> getHomeSummary() {

        // 최신 게시판 글 5개
        List<BoardEntity> recentBoardEntities = boardRepository.findTop5ByOrderByCreationDateDesc();
        List<BoardSummaryVO> recentBoards = BoardSummaryVO.getList(
            recentBoardEntities, boardLikeRepository, boardCommentRepository, userRepository
        );

        // 좋아요 기준 인기 게시판 글 5개
        List<BoardSummaryVO> popularBoards = BoardSummaryVO.getList(
            boardRepository.findAll(), boardLikeRepository, boardCommentRepository, userRepository
        ).stream()
         .sorted(Comparator.comparing(BoardSummaryVO::getLikeCount).reversed())
         .limit(5)
         .collect(Collectors.toList());

        // 최신 일상 글 5개
        List<DailyEntity> recentDailyEntities = dailyRepository.findTop5ByOrderByCreationDateDesc();
        List<DailySummaryVO> recentDailies = DailySummaryVO.getList(
            recentDailyEntities, dailyLikeRepository, dailyCommentRepository, userRepository
        );

        // 좋아요 기준 인기 일상 글 5개
        List<DailySummaryVO> popularDailies = DailySummaryVO.getList(
            dailyRepository.findAll(), dailyLikeRepository, dailyCommentRepository, userRepository
        ).stream()
         .sorted(Comparator.comparing(DailySummaryVO::getLikeCount).reversed())
         .limit(5)
         .collect(Collectors.toList());

        // 최신 중고거래 글 5개 (이미지 포함)
        List<UsedTradeEntity> recentTradeEntities = usedTradeRepository.findTop5ByOrderByCreationDateDesc();
        List<UsedTradeSummaryVO> recentTrades = UsedTradeSummaryVO.getList(
            recentTradeEntities, usedTradeLikeRepository, userRepository
        );

        // DTO에 담아서 반환
        HomeSummaryResponseDto responseDto = new HomeSummaryResponseDto(
            popularBoards, popularDailies,
            recentBoards, recentDailies,
            recentTrades
        );

        return ResponseEntity.ok(responseDto);
    }
}
