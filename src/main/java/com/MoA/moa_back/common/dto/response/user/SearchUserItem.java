package com.MoA.moa_back.common.dto.response.user;

public class SearchUserItem {
    private String userNickname;
    private String userProfileImage;

    // 생성자
    public SearchUserItem(String userNickname, String userProfileImage) {
        this.userNickname = userNickname;
        this.userProfileImage = userProfileImage;
    }

    // Getters and Setters
    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }
}