package com.MoA.moa_back.common.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.MoA.moa_back.common.dto.request.daily.PostDailyCommentRequestDto;

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
  private String creationDate;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="userId", referencedColumnName="userId", insertable = false, updatable = false)
  private UserEntity user;

  public DailyCommentEntity(PostDailyCommentRequestDto dto, Integer dailySequence, String userId) {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    this.userId = userId;
    this.dailySequence = dailySequence;
    this.creationDate = now.format(dateTimeFormatter);
    this.dailyComment = dto.getDailyComment();
  }
}

