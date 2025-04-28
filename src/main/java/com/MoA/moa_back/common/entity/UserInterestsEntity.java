package com.MoA.moa_back.common.entity;

import com.MoA.moa_back.common.dto.request.user.PatchUserInfoRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="user_interest")
@Table(name="user_interest")
public class UserInterestsEntity {
    @Id
    private String userId;
    private boolean userInterestTrip;
    private boolean userInterestGame;
    private boolean userInterestFashion;
    private boolean userInterestWorkout;
    private boolean userInterestFood;
    private boolean userInterestMusic;
    private boolean userInterestEconomics;
    private boolean userInterestNull;

    public void patch(PatchUserInfoRequestDto dto) {
        if (dto.getUserInterests() == null) return;
    
        this.userInterestTrip = dto.getUserInterests().isUserInterestTrip();
        this.userInterestGame = dto.getUserInterests().isUserInterestGame();
        this.userInterestFashion = dto.getUserInterests().isUserInterestFashion();
        this.userInterestWorkout = dto.getUserInterests().isUserInterestWorkout();
        this.userInterestFood = dto.getUserInterests().isUserInterestFood();
        this.userInterestMusic = dto.getUserInterests().isUserInterestMusic();
        this.userInterestEconomics = dto.getUserInterests().isUserInterestEconomics();
        this.userInterestNull = dto.getUserInterests().isUserInterestNull();
    }

}

