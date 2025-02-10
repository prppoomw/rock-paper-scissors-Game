package com.example.producer.controller;

import com.example.producer.component.AppMetric;
import com.example.producer.model.PlayerActionRequest;
import com.example.producer.service.RpsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private AppMetric appMetric;

    private static final Logger TESTLOGGER = LoggerFactory.getLogger("TEST_LOGGER");

    @PostMapping("/play")
    public ResponseEntity<?> playGame(@Validated @RequestBody PlayerActionRequest playerActionRequest) {
        try {
            appMetric.increasePlayRequestCounter();
            return rpsService.playGame(playerActionRequest);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("test-log")
    public ResponseEntity<?> testLog(@RequestParam(name = "log") String log) {
        switch (log) {
            case "info":
                TESTLOGGER.info("this is info log.");
                break;
            case "warn":
                TESTLOGGER.warn("this is warn log.");
                break;
            case "error":
                TESTLOGGER.error("this is error log.");
                break;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
