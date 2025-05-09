package com.MoA.moa_back.common.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserItem {
    private String userId;
    private String userNickname;
    private String userProfileImage;
}