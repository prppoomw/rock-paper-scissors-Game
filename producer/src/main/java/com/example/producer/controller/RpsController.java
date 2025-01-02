package com.example.producer.controller;

import com.example.producer.model.PlayerActionRequest;
import com.example.producer.service.RpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
