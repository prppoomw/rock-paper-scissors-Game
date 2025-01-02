package com.example.consumer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "player_score")
public class PlayerScoreEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String playerName;
    private Integer score;
}
