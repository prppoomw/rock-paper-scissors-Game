package com.example.consumer.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class CustomDeserializer implements Deserializer<JsonNode> {
    private final ObjectMapper readMapper = JsonMapper.builder()
            .build();

    @Override
    public JsonNode deserialize(String s, byte[] bytes) {
        return null;
    }

    @Override
    public JsonNode deserialize(String topic, Headers headers, byte[] data) {
        try {
            return readMapper.readTree(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

