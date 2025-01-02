package com.example.consumer.repository;

import com.example.consumer.entity.RpsResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RpsResultRepository extends JpaRepository<RpsResultEntity, Long> {
    @Query(value = "SELECT * FROM rps_result WHERE player_name = :playerName", nativeQuery = true)
    List<RpsResultEntity> findByPlayerName(@Param("playerName") String playerName);

    @Modifying
    @Query(value = "DELETE FROM rps_result WHERE player_name = :playerName", nativeQuery = true)
    void deleteByPlayerName(@Param("playerName") String playerName);
}
