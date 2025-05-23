package com.MoA.moa_back.common.entity;

import java.time.LocalDateTime;

import com.MoA.moa_back.common.dto.request.daily.PostDailyCommentRequestDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="dailyComment")
@Table(name="daily_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyCommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer commentSequence;

  private String userId;
  private Integer dailySequence;

  private String dailyComment;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="userId", referencedColumnName="userId", insertable = false, updatable = false)
  private UserEntity user;

  public DailyCommentEntity(PostDailyCommentRequestDto dto, Integer dailySequence, String userId) {
    this.userId = userId;
    this.dailySequence = dailySequence;
    this.creationDate = LocalDateTime.now();
    this.dailyComment = dto.getDailyComment();
  }
}

