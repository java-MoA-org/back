package com.MoA.moa_back.common.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;
import com.MoA.moa_back.common.enums.BoardTagType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="board")
@Table(name="board")
@Getter
@Setter
@NoArgsConstructor
public class  BoardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer boardSequence;
  
  private String userId;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;

  private String location;
  private String detailLocation;
  private String title;
  private String content;

  @ElementCollection
  @CollectionTable(name="board_images", joinColumns = @JoinColumn(name="board_sequence"))
  @Column(name="image_url")
  private List<String> images = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(length=20)
  private BoardTagType tag = BoardTagType.FREE;

  @Column(nullable=false)
  private Integer views = 0;

  public BoardEntity(PostBoardRequestDto dto, String userId) {
    this.userId = userId;
    this.creationDate = LocalDateTime.now();
    this.title = dto.getTitle();
    this.content = dto.getContent();
    this.location = dto.getLocation();
    this.detailLocation = dto.getDetailLocation();
    this.tag = dto.getTag() != null ? dto.getTag() : BoardTagType.FREE;
    this.images = dto.getImageList();
  }

  public void patch(PatchBoardRequestDto dto) {
    this.title = dto.getTitle();
    this.content = dto.getContent();
    this.location = dto.getLocation();
    this.detailLocation = dto.getDetailLocation();
    this.images = dto.getImageList();
  }
  
}