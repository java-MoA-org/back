package com.MoA.moa_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.FollowEntity;
import com.MoA.moa_back.common.entity.FollowId;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, FollowId> {
  List<FollowEntity> findByFollower(String userId);
  List<FollowEntity> findByFollowee(String userId);
  FollowEntity findByFollowerAndFollowee(String FollowerId, String FolloweeId);
}
