package com.MoA.moa_back.common.entity;

import java.time.LocalDateTime;

import com.MoA.moa_back.common.dto.request.alert.CommentAlertRequestDto;
import com.MoA.moa_back.common.dto.request.alert.FollowAlertRequestDto;
import com.MoA.moa_back.common.dto.request.alert.LikeAlertRequestDto;
import com.MoA.moa_back.common.enums.AlertType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="alert")
@Table(name="alert")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AlertEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer alertSequence;
  private String userId;
  private String content;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;
  private String link;
  private boolean isRead;
  private String type;

  public AlertEntity(CommentAlertRequestDto alertRequestDto, String userNickname, String writerId, String alertType){
    this.userId = writerId;
    this.content = userNickname + "님의 댓글 : " + alertRequestDto.getComment();
    this.creationDate = LocalDateTime.now();
    this.link = alertRequestDto.getBoardType() +"/"+ alertRequestDto.getSequence();
    this.type = alertType;
  }

  public AlertEntity(LikeAlertRequestDto alertRequestDto, String userNickname, String writerId, String alertType){
    this.userId = writerId;
    this.content = userNickname + "님이 좋아요를 누르셨습니다.";
    this.creationDate = LocalDateTime.now();
    this.link = alertRequestDto.getBoardType() + "/" + alertRequestDto.getSequence();
    this.type = alertType;
  }

  public AlertEntity(FollowAlertRequestDto followAlertRequestDto){
    System.out.println("userId:"+followAlertRequestDto.getUserId());
    this.userId = followAlertRequestDto.getUserId();
    this.content = followAlertRequestDto.getFollowerNickname() + "님이 팔로우 하셨습니다.";
    this.creationDate = LocalDateTime.now();
    this.link = "userpage/"+followAlertRequestDto.getFollowerNickname();
    this.type = AlertType.FOLLOW.toString();
  }
}



