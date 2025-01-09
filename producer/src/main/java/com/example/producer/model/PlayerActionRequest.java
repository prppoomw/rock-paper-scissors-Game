package com.example.producer.model;

import com.example.producer.enums.RpsEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerActionRequest {
    @NotNull
    @NotEmpty
    private String playerName;

    @NotNull
    @NotEmpty
    private RpsEnum action;
}
