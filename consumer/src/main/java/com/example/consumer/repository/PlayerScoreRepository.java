package com.example.consumer.repository;

import com.example.consumer.entity.PlayerScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerScoreRepository extends JpaRepository<PlayerScoreEntity, String> {
}
