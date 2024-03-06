package com.example.RPSgame;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RpsGameProducerService {


    @Autowired
    private KafkaTemplate<String, RpsGame> kafkaTemplate;

    public void sendMessage(RpsGame data){

        Message<RpsGame> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, RpsGameConstant.TOPIC_NAME)
                .build();

        kafkaTemplate.send(message);
    }
}
