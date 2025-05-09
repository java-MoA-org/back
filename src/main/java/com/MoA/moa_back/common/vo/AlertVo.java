package com.MoA.moa_back.common.vo;

import java.time.LocalDateTime;

import com.MoA.moa_back.common.entity.AlertEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class AlertVo {
    private Integer id;
    private String type;
    private String content;
    private LocalDateTime creationDate;
    private String link;
    private boolean isRead;

    public AlertVo(AlertEntity alertEntity) {
        this.id = alertEntity.getAlertSequence();
        this.type = alertEntity.getType();
        this.content = alertEntity.getContent();
        this.creationDate = alertEntity.getCreationDate();
        this.link = alertEntity.getLink();
        this.isRead = alertEntity.isRead();
    }
}
