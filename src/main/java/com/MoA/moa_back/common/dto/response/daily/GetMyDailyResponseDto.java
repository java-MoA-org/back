package com.MoA.moa_back.common.dto.response.daily;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.vo.DailyVo;
import com.MoA.moa_back.repository.DailyCommentRepository;
import com.MoA.moa_back.repository.DailyLikeRepository;

import lombok.Getter;

@Getter
public class GetMyDailyResponseDto extends ResponseDto {
  
  private List<DailyVo> dailies;
  DailyLikeRepository likeRepository;
  DailyCommentRepository commentRepository;

  private GetMyDailyResponseDto(List<DailyEntity> dailyEntities) {
    this.dailies = DailyVo.getList(dailyEntities, likeRepository, commentRepository);
  }

  public static ResponseEntity<GetMyDailyResponseDto> success(List<DailyEntity> dailyEntities) {
    GetMyDailyResponseDto body = new GetMyDailyResponseDto(dailyEntities);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }


}
