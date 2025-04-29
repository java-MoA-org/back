package com.MoA.moa_back.repository;

import org.springframework.stereotype.Repository;

import com.MoA.moa_back.common.entity.AlertEntity;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, Integer> {
    
}
