package com.MoA.moa_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.MoA.moa_back.common.dto.request.alert.CommentAlertRequestDto;
import com.MoA.moa_back.common.dto.request.alert.LikeAlertRequestDto;
import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.dto.response.alert.GetAlertResponseDto;
import com.MoA.moa_back.common.entity.AlertEntity;
import com.MoA.moa_back.common.entity.BoardEntity;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.UsedTradeEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.enums.AlertType;
import com.MoA.moa_back.common.vo.AlertVo;
import com.MoA.moa_back.repository.AlertRepository;
import com.MoA.moa_back.repository.BoardLikeRepository;
import com.MoA.moa_back.repository.BoardRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;
import com.MoA.moa_back.repository.DailyRepository;
import com.MoA.moa_back.repository.UsedTradeLikeRepository;
import com.MoA.moa_back.repository.UsedTradeRepository;
import com.MoA.moa_back.repository.UserRepository;
import com.MoA.moa_back.service.AlertService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertServiceImplement implements AlertService{

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final DailyRepository dailyRepository;
    private final DailyLikeRepository dailyLikeRepository;
    private final UsedTradeRepository usedTradeRepository;
    private final UsedTradeLikeRepository usedTradeLikeRepository;
    private final AlertRepository alertRepository;

    @Override
    public ResponseEntity<? super GetAlertResponseDto> getAlert(String userId) {
        
        List<AlertVo> alerts = new ArrayList<>();
        List<AlertEntity> alertEntities = new ArrayList<>();

        try {
            alertEntities = alertRepository.findByUserId(userId);
            for(AlertEntity alertEntity : alertEntities){
                AlertVo alert = new AlertVo(alertEntity);
                System.out.println(alert.toString());
                alerts.add(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        return GetAlertResponseDto.success(alerts);
    }

    @Override
    public ResponseEntity<ResponseDto> commentAlertPost(CommentAlertRequestDto alertRequestDto, String userId) {
        UserEntity user = null;
        String userNickname = null;
        String writerId = null;
        AlertType alertType = AlertType.COMMENT;

        try {
            user = userRepository.findByUserId(userId);
            System.out.println(user.toString());
            userNickname = user.getUserNickname();
            if(alertRequestDto.getBoardType().equals("board")){
                BoardEntity board = boardRepository.findByBoardSequence(alertRequestDto.getSequence());
                writerId = board.getUserId();
                userNickname = "익명";
            }else if(alertRequestDto.getBoardType().equals("daily")){
                DailyEntity daily = dailyRepository.findByDailySequence(alertRequestDto.getSequence());
                writerId = daily.getUserId();
            }else if(alertRequestDto.getBoardType().equals("usedTrade")){
                UsedTradeEntity usedTrade = usedTradeRepository.findByTradeSequence(alertRequestDto.getSequence());
                writerId = usedTrade.getUserId();
            }
            if(writerId.equals(userId)) return ResponseDto.sameUser();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        AlertEntity alertEntity = new AlertEntity(alertRequestDto, userNickname, writerId, alertType.toString());
        alertRepository.save(alertEntity);
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> likeAlertPost(LikeAlertRequestDto alertRequestDto, String userId) {
        UserEntity user = null;
        String userNickname = null;
        String writerId = null;
        AlertType alertType = AlertType.LIKE;
        try {
            user = userRepository.findByUserId(userId);
            System.out.println(user.toString());
            userNickname = user.getUserNickname();
            if(alertRequestDto.getBoardType().equals("board")){
                BoardEntity board = boardRepository.findByBoardSequence(alertRequestDto.getSequence());
                writerId = board.getUserId();
                userNickname = "익명";
                if(boardLikeRepository.existsByBoardSequenceAndUserId(alertRequestDto.getSequence(), userId)) return ResponseDto.cancleLike();
            }else if(alertRequestDto.getBoardType().equals("daily")){
                DailyEntity daily = dailyRepository.findByDailySequence(alertRequestDto.getSequence());
                writerId = daily.getUserId();
                if(dailyLikeRepository.existsByDailySequenceAndUserId(alertRequestDto.getSequence(), userId)) return ResponseDto.cancleLike();
            }else if(alertRequestDto.getBoardType().equals("usedTrade")){
                UsedTradeEntity usedTrade = usedTradeRepository.findByTradeSequence(alertRequestDto.getSequence());
                writerId = usedTrade.getUserId();
                if(usedTradeLikeRepository.existsByTradeSequenceAndUserId(alertRequestDto.getSequence(), userId)) return ResponseDto.cancleLike();
            }
            if(writerId.equals(userId)) return ResponseDto.sameUser();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        AlertEntity alertEntity = new AlertEntity(alertRequestDto, userNickname, writerId, alertType.toString());
        System.out.println(alertEntity.toString());
        alertRepository.save(alertEntity);
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> readAlertPatch(Integer alertId, String userId) {
        
        try {
            AlertEntity alert = alertRepository.findByAlertSequenceAndUserId(alertId, userId);
            alert.setRead(true);
            alertRepository.save(alert);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> readAllAlertPatch(String userId) {
        
        try {
            List<AlertEntity> alerts = alertRepository.findByUserId(userId);
            for(AlertEntity alert : alerts){
                alert.setRead(true);
                alertRepository.save(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> deleteAlert(Integer alertId, String userId) {
        
        try {
            AlertEntity alert = alertRepository.findByAlertSequenceAndUserId(alertId, userId);
            alertRepository.delete(alert);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> deleteAlertAll(String userId) {
        
        try {
            List<AlertEntity> alerts = alertRepository.findByUserId(userId);
            for(AlertEntity alert : alerts){
                alertRepository.delete(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }

    
    
    
}
