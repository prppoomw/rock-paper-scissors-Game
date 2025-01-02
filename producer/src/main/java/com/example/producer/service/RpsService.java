package com.example.producer.service;

import com.example.producer.model.PlayerActionRequest;
import org.springframework.http.ResponseEntity;

public interface RpsService {
    public ResponseEntity<?> playGame(PlayerActionRequest playerActionRequest);
}
