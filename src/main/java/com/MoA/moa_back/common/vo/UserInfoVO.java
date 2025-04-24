package com.MoA.moa_back.common.vo;

import com.MoA.moa_back.common.entity.UserEntity;
import com.MoA.moa_back.common.entity.UserInterestsEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoVO {
    private String userId;
    private String userNickname;
    private String userPhoneNumber;
    private String userProfileImage;
    private String userIntroduce;

    private UserInterestsEntity userInterests;

    public UserInfoVO(UserEntity userEntity, UserInterestsEntity userInterestsEntity){
        this.userId = userEntity.getUserId();
        this.userNickname = userEntity.getUserNickname();
        this.userPhoneNumber = userEntity.getUserPhoneNumber();
        this.userProfileImage = userEntity.getProfileImage();
        this.userIntroduce = userEntity.getUserIntroduce();

        this.userInterests = userInterestsEntity;
    }
}
