package com.example.producer.component;

import com.example.producer.enums.ResultEnum;
import com.example.producer.enums.RpsEnum;
import com.example.producer.model.RpsResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerUnitTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(kafkaProducer, "topic", "test-topic");
    }

    @Test
    public void testSendMessageWithMock() throws JsonProcessingException {
        // Arrange
        RpsResult rpsResult = RpsResult.builder()
                .playerName("test-player-name")
                .playerAction(RpsEnum.ROCK)
                .botAction(RpsEnum.ROCK)
                .gameResult(ResultEnum.TIE)
                .timestamp(new Date())
                .build();

        // Act
        kafkaProducer.sendMessage(rpsResult);

        // Assert
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResult = objectMapper.writeValueAsString(rpsResult);
        Mockito.verify(kafkaTemplate).send(
                Mockito.eq("test-topic"),
                Mockito.eq(rpsResult.getPlayerName()),
                Mockito.eq(expectedResult)
        );
    }
}
