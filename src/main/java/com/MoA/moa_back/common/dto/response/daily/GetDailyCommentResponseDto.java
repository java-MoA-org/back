package com.MoA.moa_back.common.dto.response.daily;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.DailyCommentEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.vo.DailyCommentVO;

import lombok.Getter;

@Getter
public class GetDailyCommentResponseDto extends ResponseDto {

  private List<DailyCommentVO> comments;

  private GetDailyCommentResponseDto(List<DailyCommentEntity> commentEntities, List<UserEntity> userEntities) {
    this.comments = DailyCommentVO.getList(commentEntities, userEntities);
  }

  public static ResponseEntity<GetDailyCommentResponseDto> success(List<DailyCommentEntity> commentEntities, List<UserEntity> userEntities) {
    GetDailyCommentResponseDto body = new GetDailyCommentResponseDto(commentEntities, userEntities);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
