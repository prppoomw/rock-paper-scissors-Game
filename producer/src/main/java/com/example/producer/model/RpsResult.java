package com.example.producer.model;

import com.example.producer.enums.ResultEnum;
import com.example.producer.enums.RpsEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class RpsResult {
    private String playerName;
    private RpsEnum playerAction;
    private RpsEnum botAction;
    private ResultEnum gameResult;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS000XXX", timezone = "Asia/Bangkok")
    private Date timestamp;
}
