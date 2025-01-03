package com.example.producer.service.impl;

import com.example.producer.component.KafkaProducer;
import com.example.producer.enums.ResultEnum;
import com.example.producer.enums.RpsEnum;
import com.example.producer.model.PlayerActionRequest;
import com.example.producer.model.RpsResult;
import com.example.producer.service.RpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Slf4j
@Service
public class RpsServiceImpl implements RpsService {

    @Autowired
    private KafkaProducer kafkaProducer;

    private final Random random = new Random();

    @Override
    public ResponseEntity<?> playGame(PlayerActionRequest playerActionRequest) {
        try {
            log.info("playerRequest = {}", playerActionRequest);
            RpsEnum botAction = getBotAction();
            ResultEnum result = getResult(playerActionRequest.getAction(), botAction);

            Date date = new Date();

            RpsResult rpsResult = RpsResult.builder()
                    .playerName(playerActionRequest.getPlayerName())
                    .playerAction(playerActionRequest.getAction())
                    .botAction(botAction)
                    .gameResult(result)
                    .timestamp(date)
                    .build();

            kafkaProducer.sendMessage(rpsResult);
            return ResponseEntity.ok().body(rpsResult);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private RpsEnum getBotAction() {
        RpsEnum[] rpsEnums = RpsEnum.values();
        return rpsEnums[random.nextInt(rpsEnums.length)];
    }

    private ResultEnum getResult(RpsEnum playerAction, RpsEnum botAction) {
        if (playerAction == botAction) {
            return ResultEnum.TIE;
        }

        return switch (playerAction) {
            case ROCK -> botAction == RpsEnum.SCISSORS ? ResultEnum.WIN : ResultEnum.LOSE;
            case PAPER -> botAction == RpsEnum.ROCK ? ResultEnum.WIN : ResultEnum.LOSE;
            case SCISSORS -> botAction == RpsEnum.PAPER ? ResultEnum.WIN : ResultEnum.LOSE;
        };
    }
}
