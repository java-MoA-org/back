package com.MoA.moa_back.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.alert.CommentAlertRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.AlertEntity;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.repository.AlertRepository;
import com.MoA.moa_back.repository.BoardRepository;
import com.MoA.moa_back.repository.DailyRepository;
import com.MoA.moa_back.repository.UsedTradeRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.AlertService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertServiceImplement implements AlertService{

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final DailyRepository dailyRepository;
    private final UsedTradeRepository usedTradeRepository;
    private final AlertRepository alertRepository;

    @Override
    public ResponseEntity<ResponseDto> commentAlertPost(CommentAlertRequestDto alertRequestDto, String userId) {

        UserEntity user = null;
        String userNickname = null;
        String writerId = null;

        try {
            user = userRepository.findByUserId(userId);
            userNickname = user.getUserNickname();
            if(alertRequestDto.getBoardType().equals("board")){
                BoardEntity board = boardRepository.findByBoardSequence(alertRequestDto.getSequence());
                writerId = board.getUserId();
            }else if(alertRequestDto.getBoardType().equals("daily")){
                DailyEntity daily = dailyRepository.findByDailySequence(alertRequestDto.getSequence());
                writerId = daily.getUserId();
            }else if(alertRequestDto.getBoardType().equals("usedTrade")){
                UsedTradeEntity usedTrade = usedTradeRepository.findByTradeSequence(alertRequestDto.getSequence());
                writerId = usedTrade.getUserId();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        AlertEntity alertEntity = new AlertEntity(alertRequestDto, userNickname, writerId);
        alertRepository.save(alertEntity);
        return ResponseDto.success(HttpStatus.OK);
    }
    
}
