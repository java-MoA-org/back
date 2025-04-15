package com.MoA.moa_back.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.mypage.GetUserBoardResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.vo.BoardSummaryVO;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.MoA.moa_back.repository.BoardRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.MyPageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImplement implements MyPageService {

     private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Override
    public ResponseEntity<? extends ResponseDto> getUserBoardList(String userNickname) {
        try {
            // 닉네임으로 유저 정보 조회
            UserEntity user = userRepository.findByUserNickname(userNickname);
            
            // userId로 게시글 조회
            List<BoardEntity> boardEntities = boardRepository.findByUserId(user.getUserId());

            // 게시글 목록을 요약 VO로 변환
            List<BoardSummaryVO> summaryList = BoardSummaryVO.getList(boardEntities, boardLikeRepository);

            return GetUserBoardResponseDto.success(summaryList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
      }
}
