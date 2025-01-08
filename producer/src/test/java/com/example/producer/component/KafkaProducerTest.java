package com.example.producer.component;

import com.example.producer.config.KafkaTestConfig;
import com.example.producer.enums.ResultEnum;
import com.example.producer.enums.RpsEnum;
import com.example.producer.model.RpsResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
@ContextConfiguration(classes = {KafkaTestConfig.class, KafkaProducer.class})
@TestPropertySource(properties = {"rps.kafka.topic=test-topic"})
public class KafkaProducerTest {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Autowired
    private KafkaTemplate<String, String> testKafkaTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaConsumer<String, String> consumer;

    @BeforeEach
    public void setup() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group-id", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(List.of("test-topic"));
    }

    @AfterEach
    public void cleanup() {
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    public void testSendMessageShouldSendMessageToKafka() throws InterruptedException, JsonProcessingException {
        // Arrange
        RpsResult rpsResult = RpsResult.builder()
                .playerName("test-player-name")
                .playerAction(RpsEnum.ROCK)
                .botAction(RpsEnum.ROCK)
                .gameResult(ResultEnum.TIE)
                .timestamp(new Date())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResult = objectMapper.writeValueAsString(rpsResult);

        // Act
        kafkaProducer.sendMessage(rpsResult);

        // Assert
        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "test-topic");
        Assertions.assertEquals(rpsResult.getPlayerName(), record.key());
        Assertions.assertEquals(expectedResult, record.value());
    }
}
