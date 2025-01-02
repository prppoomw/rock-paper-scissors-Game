package com.example.consumer.service;

import com.example.consumer.entity.PlayerScoreEntity;
import com.example.consumer.entity.RpsResultEntity;
import com.example.consumer.enums.ResultEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RpsService {
    public List<RpsResultEntity> getHistory(String playerName);
    public Optional<PlayerScoreEntity> getScore(String playerName);
    public ResponseEntity<?> reset(String playerName);
    public RpsResultEntity saveGameResult(RpsResultEntity rpsResultEntity);
    public PlayerScoreEntity updatePlayerScore(String playerName, ResultEnum result);
    public List<RpsResultEntity> updatePlayerGameHistory(String playerName);
}
