package com.example.producer.component;

import com.example.producer.model.RpsResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${rps.kafka.topic}")
    private String topic;

    private final ObjectMapper writeMapper = JsonMapper.builder()
            .disable(SerializationFeature.INDENT_OUTPUT)
            .build();

    public void sendMessage(RpsResult rpsResult) {
        try {
            String data = writeMapper.writeValueAsString(rpsResult);
            String key = rpsResult.getPlayerName();
            kafkaTemplate.send(topic, key, data);
        } catch (KafkaException | JsonProcessingException e) {
            log.error("error occurs while sending message: {}, error: {}", rpsResult, e.getMessage());
            throw new InternalException(e);
        }
    }
}
