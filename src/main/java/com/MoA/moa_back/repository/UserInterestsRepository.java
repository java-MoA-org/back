package com.MoA.moa_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.UserInterestsEntity;

@Repository
public interface UserInterestsRepository extends JpaRepository<UserInterestsEntity, String>{
    
    
}
