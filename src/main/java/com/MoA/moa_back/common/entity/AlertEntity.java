package com.MoA.moa_back.common.entity;

import java.time.LocalDateTime;

import com.MoA.moa_back.common.dto.request.alert.CommentAlertRequestDto;
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

@Entity(name="alert")
@Table(name="alert")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
  private AlertType type;

  public AlertEntity(CommentAlertRequestDto alertRequestDto, String userNickname, String writerId){
    this.userId = writerId;
    this.content = userNickname + "님의 댓글 : " + alertRequestDto.getComment();
    this.creationDate = LocalDateTime.now();
    this.link = "localhost:3000/"+alertRequestDto.getBoardType() +"/"+ alertRequestDto.getSequence();
    this.type = AlertType.COMMENT;
  }
}



