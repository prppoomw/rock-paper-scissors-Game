package com.example.consumer.entity;

import com.example.consumer.enums.ResultEnum;
import com.example.consumer.enums.RpsEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "rps_result")
public class RpsResultEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String playerName;
    private RpsEnum playerAction;
    private RpsEnum botAction;
    private ResultEnum gameResult;
    private Date timestamp;
}
