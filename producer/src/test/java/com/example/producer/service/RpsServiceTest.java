package com.example.producer.service;

import com.example.producer.component.KafkaProducer;
import com.example.producer.enums.ResultEnum;
import com.example.producer.enums.RpsEnum;
import com.example.producer.model.PlayerActionRequest;
import com.example.producer.model.RpsResult;
import com.example.producer.service.impl.RpsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RpsServiceTest {

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private RpsServiceImpl rpsService;

    @Spy
    private Random random;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(rpsService, "random", random);
    }

    @Test
    public void testPlayGameOK() {
        // Arrange
        PlayerActionRequest playerActionRequest = PlayerActionRequest.builder()
                .playerName("test-player-name")
                .action(RpsEnum.ROCK)
                .build();
        when(random.nextInt(RpsEnum.values().length)).thenReturn(RpsEnum.PAPER.ordinal());

        // Act
        ResponseEntity<?> response = rpsService.playGame(playerActionRequest);

        // Assert
        RpsResult expectedRpsResultResponse = RpsResult.builder()
                .playerName("test-player-name")
                .playerAction(RpsEnum.ROCK)
                .botAction(RpsEnum.PAPER)
                .gameResult(ResultEnum.LOSE)
                .build();
        RpsResult actualRpsResultResponse = (RpsResult) response.getBody();
        Assertions.assertNotNull(actualRpsResultResponse);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedRpsResultResponse.getPlayerName(), actualRpsResultResponse.getPlayerName());
        Assertions.assertEquals(expectedRpsResultResponse.getPlayerAction(), actualRpsResultResponse.getPlayerAction());
        Assertions.assertEquals(expectedRpsResultResponse.getBotAction(), actualRpsResultResponse.getBotAction());
        Assertions.assertEquals(expectedRpsResultResponse.getGameResult(), actualRpsResultResponse.getGameResult());
    }

    @Test
    public void testPlayGameInternalServerError() {
        // Arrange
        PlayerActionRequest playerActionRequest = PlayerActionRequest.builder()
                .playerName(null)
                .action(null)
                .build();

        // Act
        ResponseEntity<?> response = rpsService.playGame(playerActionRequest);

        // Assert
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
