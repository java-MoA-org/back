package com.MoA.moa_back.common.entity;

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
}
