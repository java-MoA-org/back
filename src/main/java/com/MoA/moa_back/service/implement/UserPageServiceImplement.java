package com.MoA.moa_back.service.implement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.PatchUserInfoRequestDto;
import com.MoA.moa_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.mypage.GetUserPageResponseDto;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.entity.UserInterestsEntity;
import com.MoA.moa_back.common.vo.UserInterestVO;
import com.MoA.moa_back.common.vo.summary.BoardSummaryVO;
import com.MoA.moa_back.common.vo.summary.DailySummaryVO;
import com.MoA.moa_back.common.vo.summary.UsedTradeSummaryVO;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.MoA.moa_back.repository.BoardRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;
import com.MoA.moa_back.repository.DailyRepository;
import com.MoA.moa_back.repository.UsedTradeLikeRepository;
import com.MoA.moa_back.repository.UsedTradeRepository;
import com.MoA.moa_back.repository.UserInterestsRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.UserPageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPageServiceImplement implements UserPageService {

     private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final DailyRepository dailyRepository;
    private final DailyLikeRepository dailyLikeRepository;

    private final UserInterestsRepository userInterestsRepository;

    private final UsedTradeRepository usedTradeRepository;
    private final UsedTradeLikeRepository usedTradeLikeRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public ResponseEntity<? super GetUserPageResponseDto> getUserBoardList(String userNickname) {
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

                // 5. 관심사 조회
                UserInterestsEntity interestEntity = userInterestsRepository.findByUserId(userId);
                UserInterestVO interests = new UserInterestVO(interestEntity);

                // 6. 자기소개 조회
                String userIntroduce = user.getUserIntroduce();
    
                // 5. 응답 반환
                return GetUserPageResponseDto.success(boards, dailys, trades, interests, userIntroduce);
    
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseDto.databaseError();
            }
      }

    @Override
    public ResponseEntity<ResponseDto> patchUserInfo(PatchUserInfoRequestDto dto, String userNickname) {
        try{
            UserEntity userEntity = userRepository.findByUserNickname(userNickname);
            if (userEntity == null) return ResponseDto.noExistUser();

            // 닉네임 변경 검증
            if (dto.getUserNickname() != null && !dto.getUserNickname().equals(userEntity.getUserNickname())) {
                boolean isExist = userRepository.existsByUserNickname(dto.getUserNickname());
                if (isExist) return ResponseDto.existUserNickname();
            }
    
            // 비밀번호 변경 조건 검증
            if (dto.getUserPassword() != null && !dto.getUserPassword().isBlank()) {
                if (dto.getCurrentPassword() == null || !passwordEncoder.matches(dto.getCurrentPassword(), userEntity.getUserPassword())) {
                    return ResponseDto.noPermission(); // 현재 비밀번호가 틀림
                }
            }
            userEntity.patch(dto, passwordEncoder);
            userRepository.save(userEntity);
            
        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> nicknameCheck(NicknameCheckRequestDto dto) {
        try{
            boolean isExist = userRepository.existsByUserNickname(dto.getUserNickname());
            if(isExist) return ResponseDto.existUserNickname();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }
}
