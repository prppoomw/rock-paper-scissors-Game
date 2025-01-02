package com.example.producer.model;

import com.example.producer.enums.RpsEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlayerActionRequest {
    @NotNull
    @NotEmpty
    private String playerName;

    @NotNull
    @NotEmpty
    private RpsEnum action;
}
