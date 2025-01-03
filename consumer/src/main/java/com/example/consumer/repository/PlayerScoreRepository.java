package com.example.consumer.repository;

import com.example.consumer.entity.PlayerScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerScoreRepository extends JpaRepository<PlayerScoreEntity, String> {
}
