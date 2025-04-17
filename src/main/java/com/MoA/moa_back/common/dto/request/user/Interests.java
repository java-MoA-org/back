package com.MoA.moa_back.common.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interests {

    private boolean userInterestTrip;
    private boolean userInterestGame;
    private boolean userInterestFashion;
    private boolean userInterestWorkout;
    private boolean userInterestFood;
    private boolean userInterestMusic;
    private boolean userInterestEconomics;
    private boolean userInterestNull;

}