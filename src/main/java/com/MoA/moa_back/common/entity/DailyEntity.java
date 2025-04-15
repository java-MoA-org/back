package com.MoA.moa_back.common.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.MoA.moa_back.common.dto.request.daily.PatchDailyRequestDto;
import com.MoA.moa_back.common.dto.request.daily.PostDailyRequestDto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name="dailyBoard")
@Table(name="daily_board")
@Getter
@Setter
@NoArgsConstructor
public class DailyEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer dailySequence;
  private String userId;
  private String creationDate;
  private String location;
  private String detailLocation;
  private String title;
  private String content;

  @ElementCollection
  @CollectionTable(name="daily_images", joinColumns = @JoinColumn(name="daily_sequence"))
  @Column(name="image_url")
  private List<String> images = new ArrayList<>();

  @Column(nullable=false)
  private Integer views = 0;

  public DailyEntity(PostDailyRequestDto dto, String userId) {
    LocalDate now = LocalDate.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    this.userId = userId;
    this.creationDate = now.format(dateTimeFormatter);
    this.title = dto.getTitle();
    this.content = dto.getContent();
    this.location = dto.getLocation();
    this.detailLocation = dto.getDetailLocation();
    this.images = dto.getImageList() != null ? dto.getImageList() : new ArrayList<>();
  }

  public void patch(PatchDailyRequestDto dto) {
    this.title = dto.getTitle();
    this.content = dto.getContent();
    this.location = dto.getLocation();
    this.detailLocation = dto.getDetailLocation();
    this.images = dto.getImageList() != null ? dto.getImageList() : new ArrayList<>();
  }
  
}
