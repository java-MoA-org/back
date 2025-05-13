package com.MoA.moa_back.common.dto.response.daily;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.MoA.moa_back.common.dto.response.ResponseDto;
import com.MoA.moa_back.common.entity.DailyEntity;
import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.vo.DailyCommentVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetDailyResponseDto extends ResponseDto {
  private Integer dailySequence;
  private String title;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  
  private String profileImage;
  private String writerNickname;
  private Integer views;
  private Integer likeCount;
  private List<DailyCommentVO> comments;
  private Boolean liked;

  public static GetDailyResponseDto of(
    DailyEntity daily,
    int likeCount,
    List<DailyCommentVO> commentList,
    UserEntity user,
    Boolean liked
  ) {
    return new GetDailyResponseDto(
      daily.getDailySequence(),
      daily.getTitle(),
      daily.getContent(),
      daily.getCreationDate(),
      user.getProfileImage(),
      user.getUserNickname(),
      daily.getViews(),
      likeCount,
      commentList,
      liked
    );
  }

  public static ResponseEntity<GetDailyResponseDto> success(
    DailyEntity daily,
    int likeCount,
    List<DailyCommentVO> commentList,
    UserEntity user,
    Boolean liked
  ) {
    GetDailyResponseDto body = GetDailyResponseDto.of(daily, likeCount, commentList, user, liked);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
  
}
