package com.example.producer.controller;

import com.example.producer.enums.ResultEnum;
import com.example.producer.enums.RpsEnum;
import com.example.producer.model.PlayerActionRequest;
import com.example.producer.model.RpsResult;
import com.example.producer.service.RpsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RpsController.class)
public class RpsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RpsService rpsService;

    @Test
    public void whenPlayGameThenSuccessful() throws Exception {
        // Arrange
        PlayerActionRequest playerActionRequest = PlayerActionRequest.builder()
                .playerName("test-player-name")
                .action(RpsEnum.ROCK)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(playerActionRequest);

        // Act & Assert
        RpsResult rpsResult = RpsResult.builder()
                .playerName("test-player-name")
                .playerAction(RpsEnum.ROCK)
                .botAction(RpsEnum.PAPER)
                .gameResult(ResultEnum.LOSE)
                .build();

        doReturn(ResponseEntity.ok().body(rpsResult)).when(rpsService).playGame(any(PlayerActionRequest.class));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/rps-producer/play")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerName").value(rpsResult.getPlayerName()));
    }

    @Test
    public void whenPlayGameMissSomeFieldBadRequest() throws Exception {
        // Arrange
        PlayerActionRequest playerActionRequest = PlayerActionRequest.builder()
                .playerName("test-player-name")
                .action(null)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(playerActionRequest);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/rps-producer/play")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPlayGameAndInternalErrorThenInternalServerError() throws Exception {
        // Arrange
        PlayerActionRequest playerActionRequest = PlayerActionRequest.builder()
                .playerName("test-player-name")
                .action(RpsEnum.ROCK)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(playerActionRequest);
        doThrow(new RuntimeException("Internal server error")).when(rpsService).playGame(any(PlayerActionRequest.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/rps-producer/play")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(payload))
                .andExpect(status().isInternalServerError());
    }
}
