package com.MoA.moa_back.common.dto.response.notice;

import com.MoA.moa_back.common.entity.NotificationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeListItem {
    
    private int notificationSequence;
    private String title;
    private String creationDate;
    private int views;

    public NoticeListItem(NotificationEntity entity) {
        this.notificationSequence = entity.getNotificationSequence();
        this.title = entity.getTitle();
        this.creationDate = entity.getCreationDate().toString();
        this.views = entity.getViews();
    }
}