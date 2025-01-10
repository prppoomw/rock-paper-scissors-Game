package com.example.producer.model;

import com.example.producer.enums.RpsEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerActionRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    @JsonProperty("playerName")
    private String playerName;

    @NotNull
    @JsonProperty("action")
    private RpsEnum action;
}
