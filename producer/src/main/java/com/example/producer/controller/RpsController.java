package com.example.producer.controller;

import com.example.producer.model.PlayerActionRequest;
import com.example.producer.service.RpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/rps-producer")
public class RpsController {

    @Autowired
    private RpsService rpsService;

    @PostMapping("/play")
    public ResponseEntity<?> playGame(@Validated @RequestBody PlayerActionRequest playerActionRequest) {
        try {
            return rpsService.playGame(playerActionRequest);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
