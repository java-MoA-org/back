package com.MoA.moa_back.common.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode //  쓰는 이유 : 두키가 같은지 그리고 해시코드가 같은지 비교하기위해
public class FollowId implements Serializable {
    private String follower;
    private String followee;
}

