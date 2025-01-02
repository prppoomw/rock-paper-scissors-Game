package com.example.producer.controller;

import com.example.producer.model.PlayerActionRequest;
import com.example.producer.model.RpsResult;
import com.example.producer.service.RpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rps-producer")
public class RpsController {

    @Autowired
    private RpsService rpsService;

    @PostMapping("/play")
    public ResponseEntity<?> playGame(@RequestBody PlayerActionRequest playerActionRequest) {
        return rpsService.playGame(playerActionRequest);
    }
}
