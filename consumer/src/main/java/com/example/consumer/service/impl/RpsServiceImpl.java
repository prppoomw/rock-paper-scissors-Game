package com.example.consumer.service.impl;

import com.example.consumer.entity.PlayerScoreEntity;
import com.example.consumer.entity.RpsResultEntity;
import com.example.consumer.enums.ResultEnum;
import com.example.consumer.repository.PlayerScoreRepository;
import com.example.consumer.repository.RpsResultRepository;
import com.example.consumer.service.RpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RpsServiceImpl implements RpsService {

    @Autowired
    private RpsResultRepository rpsResultRepository;

    @Autowired
    private PlayerScoreRepository playerScoreRepository;

    @Cacheable(value = "playerGameHistory", key = "#playerName")
    @Override
    public List<RpsResultEntity> getHistory(String playerName) {
        return rpsResultRepository.findByPlayerName(playerName);
    }

    @Cacheable(value = "playerScore", key = "#playerName")
    @Override
    public Optional<PlayerScoreEntity> getScore(String playerName) {
        return playerScoreRepository.findById(playerName);
    }

    @Transactional
    @CacheEvict(value = {"playerGameHistory", "playerScore"}, key = "#playerName")
    @Override
    public ResponseEntity<?> reset(String playerName) {
        rpsResultRepository.deleteByPlayerName(playerName);
        playerScoreRepository.deleteById(playerName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public RpsResultEntity saveGameResult(RpsResultEntity rpsResultEntity) {
        return rpsResultRepository.save(rpsResultEntity);
    }

    @CachePut(value = "playerGameHistory", key = "#playerName")
    @Override
    public List<RpsResultEntity> updatePlayerGameHistory(String playerName) {
        return rpsResultRepository.findByPlayerName(playerName);
    }

    @CachePut(value = "playerScore", key = "#playerName")
    @Override
    public PlayerScoreEntity updatePlayerScore(String playerName, ResultEnum result) {
        PlayerScoreEntity playerScoreEntity = playerScoreRepository.findById(playerName).orElse(new PlayerScoreEntity());
        switch (result) {
            case WIN -> {
                playerScoreEntity.setPlayerName(playerName);
                playerScoreEntity.setScore(playerScoreEntity.getScore() == null ? 1 : playerScoreEntity.getScore() + 1);
                playerScoreRepository.save(playerScoreEntity);
            }
            case LOSE -> {
                playerScoreEntity.setPlayerName(playerName);
                int newScore = playerScoreEntity.getScore() == null ? 0 : playerScoreEntity.getScore() - 1;
                playerScoreEntity.setScore(Math.max(newScore, 0));
                playerScoreRepository.save(playerScoreEntity);
            }
            case TIE -> {
                playerScoreEntity.setPlayerName(playerName);
                playerScoreEntity.setScore(playerScoreEntity.getScore() == null ? 0 : playerScoreEntity.getScore());
                playerScoreRepository.save(playerScoreEntity);
            }
        }
        return playerScoreEntity;
    }
}
