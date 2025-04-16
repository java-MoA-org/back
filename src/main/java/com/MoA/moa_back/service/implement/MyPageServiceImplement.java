package com.MoA.moa_back.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.mypage.GetUserPageResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.vo.summary.BoardSummaryVO;
import com.MoA.moa_back.common.vo.summary.DailySummaryVO;
import com.MoA.moa_back.common.vo.summary.UsedTradeSummaryVO;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.MoA.moa_back.repository.BoardRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;
import com.MoA.moa_back.repository.DailyRepository;
import com.MoA.moa_back.repository.UsedTradeLikeRepository;
import com.MoA.moa_back.repository.UsedTradeRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.MyPageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImplement implements MyPageService {

     private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final DailyRepository dailyRepository;
    private final DailyLikeRepository dailyLikeRepository;

    private final UsedTradeRepository usedTradeRepository;
    private final UsedTradeLikeRepository usedTradeLikeRepository;

    @Override
    public ResponseEntity<? extends ResponseDto> getUserBoardList(String userNickname) {
       try {
                // 1. 유저 정보 조회
                UserEntity user = userRepository.findByUserNickname(userNickname);
                if (user == null) return ResponseDto.noExistUser();
                String userId = user.getUserId();
    
                // 2. 게시판(익명) 리스트
                List<BoardEntity> boardEntities = boardRepository.findByUserId(userId);
                List<BoardSummaryVO> boards = BoardSummaryVO.getList(boardEntities, boardLikeRepository);
    
                // 3. 일상 게시판 리스트
                List<DailyEntity> dailyEntities = dailyRepository.findByUserId(userId);
                List<DailySummaryVO> dailys = DailySummaryVO.getList(dailyEntities, dailyLikeRepository);
    
                // 4. 중고거래 리스트
                List<UsedTradeEntity> tradeEntities = usedTradeRepository.findByUserId(userId);
                List<UsedTradeSummaryVO> trades = UsedTradeSummaryVO.getList(tradeEntities, usedTradeLikeRepository);
    
                // 5. 응답 반환
                return GetUserPageResponseDto.success(boards, dailys, trades);
    
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseDto.databaseError();
            }
      }
}
