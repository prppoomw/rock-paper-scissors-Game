package com.example.consumer.component;

import com.example.consumer.entity.PlayerScoreEntity;
import com.example.consumer.entity.RpsResultEntity;
import com.example.consumer.service.RpsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private RpsService rpsService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${rps.kafka.topic}", groupId = "${rps.kafka.group_id}")
    public void consumeMessage(ConsumerRecord<String, JsonNode> record) {
        try {
            log.info("consume message: offset {}, key {}, value {}", record.offset(), record.key(), record.value());
            saveResult(record);
        } catch (KafkaException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }

    private void saveResult(ConsumerRecord<String, JsonNode> record) {
        try {
            RpsResultEntity rpsResultEntity = objectMapper.treeToValue(record.value(), RpsResultEntity.class);

            RpsResultEntity playerResult = rpsService.saveGameResult(rpsResultEntity);
            log.info("save player game result: {}", playerResult);

            PlayerScoreEntity playerScoreEntity = rpsService.updatePlayerScore(rpsResultEntity.getPlayerName(), rpsResultEntity.getGameResult());
            log.info("update player score: player {}, score {}", playerScoreEntity.getPlayerName(), playerScoreEntity.getScore() );

            List<RpsResultEntity> resultList = rpsService.updatePlayerGameHistory(rpsResultEntity.getPlayerName());
            log.info("update result: player {}, history {}", rpsResultEntity.getPlayerName(), resultList);
        } catch (JsonProcessingException e) {
            log.error("json processing error of message: {}", record, e);
        }
    }
}
