package com.example.consumer.component;

import com.example.consumer.entity.PlayerScoreEntity;
import com.example.consumer.entity.RpsResultEntity;
import com.example.consumer.enums.ResultEnum;
import com.example.consumer.enums.RpsEnum;
import com.example.consumer.service.RpsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerUnitTest {

    @Mock
    private RpsService rpsService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Captor
    private ArgumentCaptor<RpsResultEntity> rpsResultEntityCaptor;

    @Test
    public void testConsumeMessageWithMockSuccess() {
        // Arrange
        RpsResultEntity rpsResult= new RpsResultEntity();
        rpsResult.setId(1L);
        rpsResult.setPlayerName("test-player-name");
        rpsResult.setPlayerAction(RpsEnum.ROCK);
        rpsResult.setBotAction(RpsEnum.ROCK);
        rpsResult.setGameResult(ResultEnum.TIE);
        rpsResult.setTimestamp(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(rpsResult);
        ConsumerRecord<String, JsonNode> record = new ConsumerRecord<>("test-topic", 0, 0L, rpsResult.getPlayerName(), jsonNode);

        PlayerScoreEntity playerScore = new PlayerScoreEntity();
        playerScore.setPlayerName(rpsResult.getPlayerName());
        playerScore.setScore(0);

        when(rpsService.saveGameResult(any(RpsResultEntity.class))).thenReturn(rpsResult);
        when(rpsService.updatePlayerScore(eq(rpsResult.getPlayerName()), eq(rpsResult.getGameResult()))).thenReturn(playerScore);
        when(rpsService.updatePlayerGameHistory(eq(rpsResult.getPlayerName()))).thenReturn(Collections.singletonList(rpsResult));

        // Act
        kafkaConsumer.consumeMessage(record);

        // Assert
        verify(rpsService).saveGameResult(rpsResultEntityCaptor.capture());
        verify(rpsService).updatePlayerScore(rpsResult.getPlayerName(), ResultEnum.TIE);
        verify(rpsService).updatePlayerGameHistory(rpsResult.getPlayerName());

        RpsResultEntity capturedEntity = rpsResultEntityCaptor.getValue();
        Assertions.assertEquals(rpsResult.getId(), capturedEntity.getId());
        Assertions.assertEquals(rpsResult.getPlayerName(), capturedEntity.getPlayerName());
        Assertions.assertEquals(rpsResult.getPlayerAction(), capturedEntity.getPlayerAction());
        Assertions.assertEquals(rpsResult.getBotAction(), capturedEntity.getBotAction());
        Assertions.assertEquals(rpsResult.getGameResult(), capturedEntity.getGameResult());
        Assertions.assertEquals(rpsResult.getTimestamp(), capturedEntity.getTimestamp());
    }

    @Test
    public void testConsumeMessageWithJsonProcessingFailed() throws JsonProcessingException {
        // Arrange
        String topic = "test-topic";
        JsonNode invalidJson = new ObjectMapper().readTree("{\"invalid\":\"data\"}");
        ConsumerRecord<String, JsonNode> record = new ConsumerRecord<>("test-topic", 0, 0L, "test-key", invalidJson);

        // Act
        kafkaConsumer.consumeMessage(record);

        // Assert
        verifyNoInteractions(rpsService);
    }

    @Test
    public void testConsumeMessageWithKafkaException() throws Exception {
        // Arrange
        RpsResultEntity rpsResult= new RpsResultEntity();
        rpsResult.setId(1L);
        rpsResult.setPlayerName("test-player-name");
        rpsResult.setPlayerAction(RpsEnum.ROCK);
        rpsResult.setBotAction(RpsEnum.ROCK);
        rpsResult.setGameResult(ResultEnum.TIE);
        rpsResult.setTimestamp(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(rpsResult);
        ConsumerRecord<String, JsonNode> record = new ConsumerRecord<>("test-topic", 0, 0L, rpsResult.getPlayerName(), jsonNode);

        when(rpsService.saveGameResult(any(RpsResultEntity.class))).thenThrow(new KafkaException("test kafka exception"));

        // Act & Asset
        KafkaException exception = assertThrows(KafkaException.class, () -> {kafkaConsumer.consumeMessage(record);});

        Assertions.assertEquals("test kafka exception", exception.getMessage());
        verify(rpsService).saveGameResult(any(RpsResultEntity.class));
        verifyNoMoreInteractions(rpsService);
    }
}
