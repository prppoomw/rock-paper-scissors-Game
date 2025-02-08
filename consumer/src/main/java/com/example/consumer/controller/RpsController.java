package com.example.consumer.controller;

import com.example.consumer.component.AppMetric;
import com.example.consumer.entity.PlayerScoreEntity;
import com.example.consumer.entity.RpsResultEntity;
import com.example.consumer.service.RpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/rps-consumer")
public class RpsController {

    @Autowired
    private RpsService rpsService;

    @Autowired
    private AppMetric appMetric;

    @GetMapping("/history")
    public List<RpsResultEntity> getGameHistory(@RequestParam(name = "playerName") String playerName){
        appMetric.increaseGameHistoryRequestCounter();
        return rpsService.getHistory(playerName);
    }

    @GetMapping("/score")
    public Optional<PlayerScoreEntity> getGameScore(@RequestParam(name = "playerName") String playerName){
        appMetric.increaseGameScoreRequestCounter();
        return rpsService.getScore(playerName);
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> resetGame(@RequestParam(name = "playerName") String playerName){
        appMetric.increaseResetGameRequestCounter();
        return rpsService.reset(playerName);
    }
}
