package com.MoA.moa_back.common.dto.response.notice;

import com.MoA.moa_back.common.entity.NotificationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class GetNoticeResponseDto {
    private String title;
    private String content;
    private int views;
    private LocalDateTime creationDate;

    public GetNoticeResponseDto(NotificationEntity entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.views = entity.getViews();
        this.creationDate = entity.getCreationDate();
    }

    public static ResponseEntity<GetNoticeResponseDto> success(NotificationEntity entity) {
    GetNoticeResponseDto body = new GetNoticeResponseDto(entity);
    return ResponseEntity.status(HttpStatus.OK).body(body);
}
}