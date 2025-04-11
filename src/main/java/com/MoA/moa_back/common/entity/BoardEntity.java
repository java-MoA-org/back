package com.MoA.moa_back.common.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.MoA.moa_back.common.dto.request.board.PatchBoardRequestDto;
import com.MoA.moa_back.common.dto.request.board.PostBoardRequestDto;

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
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardSequence;
    private String userId;
    private String creationDate;
    private String location;
    private String detailLocation;
    private String title;
    private String content;

    @ElementCollection
    @CollectionTable(name="board_images", joinColumns = @JoinColumn(name="board_sequence"))
    @Column(name="image_url")
    private List<String> images = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TagType tag = TagType.자유;
    private Integer views = 0;

    public BoardEntity(PostBoardRequestDto dto, String userId) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.userId = userId;
        this.creationDate = now.format(dateTimeFormatter);
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.location = dto.getLocation();
        this.detailLocation = dto.getDetailLocation();
        this.tag = dto.getTag() != null ? dto.getTag() : TagType.자유;
        this.images = dto.getImageList() != null ? dto.getImageList() : new ArrayList<>();
    }

    public void patch(PatchBoardRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.location = dto.getLocation();
        this.detailLocation = dto.getDetailLocation();
        this.images = dto.getImageList() != null ? dto.getImageList() : new ArrayList<>();
    }
}
