package com.example.consumer.service;

import com.example.consumer.entity.PlayerScoreEntity;
import com.example.consumer.entity.RpsResultEntity;
import com.example.consumer.enums.ResultEnum;
import com.example.consumer.enums.RpsEnum;
import com.example.consumer.repository.PlayerScoreRepository;
import com.example.consumer.repository.RpsResultRepository;
import com.example.consumer.service.impl.RpsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RpsServiceTest {

    @Mock
    private RpsResultRepository rpsResultRepository;

    @Mock
    private PlayerScoreRepository playerScoreRepository;

    @InjectMocks
    private RpsServiceImpl rpsService;

    @Test
    public void whenGetHistoryThenReturnPlayerHistory() {
        // Arrange
        String playerName = "test-player-name";
        RpsResultEntity rpsResult= new RpsResultEntity();
        rpsResult.setId(1L);
        rpsResult.setPlayerName("test-player-name");
        rpsResult.setPlayerAction(RpsEnum.ROCK);
        rpsResult.setBotAction(RpsEnum.ROCK);
        rpsResult.setGameResult(ResultEnum.TIE);
        rpsResult.setTimestamp(new Date());
        List<RpsResultEntity> resultEntityList = List.of(rpsResult);

        when(rpsResultRepository.findByPlayerName(playerName)).thenReturn(resultEntityList);

        // Act
        List<RpsResultEntity> result = rpsService.getHistory(playerName);

        // Assert
        Assertions.assertEquals(resultEntityList, result);
    }

    @Test
    public void whenGetScoreThenReturnPlayerScore() {
        // Arrange
        String playerName = "test-player-name";
        PlayerScoreEntity playerScore = new PlayerScoreEntity();
        playerScore.setPlayerName("test-player-name");
        playerScore.setScore(0);
        Optional<PlayerScoreEntity> optionalPlayerScore = Optional.of(playerScore);

        when(playerScoreRepository.findById(playerName)).thenReturn(optionalPlayerScore);

        // Act
        Optional<PlayerScoreEntity> result = rpsService.getScore(playerName);

        // Assert
        Assertions.assertEquals(optionalPlayerScore, result);
    }

    @Test
    public void whenResetThenDeletePlayerResultAndScore() {
        // Arrange
        String playerName = "test-player-name";

        // Act
        ResponseEntity<?> response = rpsService.reset(playerName);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(rpsResultRepository, Mockito.times(1)).deleteByPlayerName(anyString());
        Mockito.verify(playerScoreRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void whenSaveGameResultThenSuccess() {
        // Arrange
        RpsResultEntity rpsResult= new RpsResultEntity();
        rpsResult.setId(1L);
        rpsResult.setPlayerName("test-player-name");
        rpsResult.setPlayerAction(RpsEnum.ROCK);
        rpsResult.setBotAction(RpsEnum.ROCK);
        rpsResult.setGameResult(ResultEnum.TIE);
        rpsResult.setTimestamp(new Date());

        when(rpsResultRepository.save(rpsResult)).thenReturn(rpsResult);

        // Act
        RpsResultEntity rpsResultResponse = rpsService.saveGameResult(rpsResult);

        // Assert
        Assertions.assertEquals(rpsResult, rpsResultResponse);
    }

    @Test
    public void whenUpdateCacheThenReturnPlayerResult() {
        // Arrange
        String playerName = "test-player-name";
        RpsResultEntity rpsResult= new RpsResultEntity();
        rpsResult.setId(1L);
        rpsResult.setPlayerName("test-player-name");
        rpsResult.setPlayerAction(RpsEnum.ROCK);
        rpsResult.setBotAction(RpsEnum.ROCK);
        rpsResult.setGameResult(ResultEnum.TIE);
        rpsResult.setTimestamp(new Date());
        List<RpsResultEntity> resultEntityList = List.of(rpsResult);

        when(rpsResultRepository.findByPlayerName(playerName)).thenReturn(resultEntityList);

        // Act
        List<RpsResultEntity> result = rpsService.updatePlayerGameHistory(playerName);

        // Assert
        Assertions.assertEquals(resultEntityList, result);
    }

    @Test
    public void whenUpdateScoreThenUpdatePlayerScoreSuccess() {
        // Arrange
        String playerName = "test-player-name";
        ResultEnum resultEnum = ResultEnum.TIE;

        PlayerScoreEntity playerScore = new PlayerScoreEntity();
        playerScore.setPlayerName(playerName);
        playerScore.setScore(0);

        when(playerScoreRepository.findById(playerName)).thenReturn(Optional.of(playerScore));

        // Act
        PlayerScoreEntity playerScoreResponse = rpsService.updatePlayerScore(playerName, resultEnum);

        // Assert
        Assertions.assertEquals(playerScore.getPlayerName(), playerScoreResponse.getPlayerName());
        Assertions.assertEquals(playerScore.getScore(), playerScoreResponse.getScore());

    }

}
