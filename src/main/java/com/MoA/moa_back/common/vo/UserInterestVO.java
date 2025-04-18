package com.MoA.moa_back.common.vo;

import com.MoA.moa_back.common.entity.UserInterestsEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInterestVO {
    private boolean userInterestTrip;
    private boolean userInterestGame;
    private boolean userInterestFashion;
    private boolean userInterestWorkout;
    private boolean userInterestFood;
    private boolean userInterestMusic;
    private boolean userInterestEconomics;
    private boolean userInterestNull;

    public UserInterestVO(UserInterestsEntity entity) {
      if (entity == null) {
        this.userInterestNull = true;
      } else {
        this.userInterestTrip = entity.isUserInterestTrip();
        this.userInterestGame = entity.isUserInterestGame();
        this.userInterestFashion = entity.isUserInterestFashion();
        this.userInterestWorkout = entity.isUserInterestWorkout();
        this.userInterestFood = entity.isUserInterestFood();
        this.userInterestMusic = entity.isUserInterestMusic();
        this.userInterestEconomics = entity.isUserInterestEconomics();
        this.userInterestNull = entity.isUserInterestNull();
      }
    }
}

