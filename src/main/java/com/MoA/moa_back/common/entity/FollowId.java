package com.MoA.moa_back.common.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FollowId implements Serializable {
    private String follower;
    private String followee;
}

