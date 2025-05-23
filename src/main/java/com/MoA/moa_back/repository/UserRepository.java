package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUserId(String userId);
    boolean existsByUserNickname(String userNickname);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserPhoneNumber(String userPhoneNumber);
    
    UserEntity findByUserId(String userId);

    UserEntity findByUserNickname(String userNickname);
    UserEntity findByUserIdAndJoinType(String userId, String joinType);

    UserEntity findUserIdByUserEmail(String userEmail);

    List<UserEntity> findByUserIdIn(List<String> userIds);

    List<UserEntity> findByUserNicknameContaining(String keyword);

}

