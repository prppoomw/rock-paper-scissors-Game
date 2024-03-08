package com.example.RPSgame;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class RpsGameConsumerService {
	//private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RpsGameConsumerService.class);

	@Autowired
	private RpsGameRepository rpsGameRepository;
	//private RpsGameService rpsGameService;
	
	@KafkaListener(topics = RpsGameConstant.TOPIC_NAME, groupId = RpsGameConstant.GROUP_ID)
	public void consumeMessage(RpsGame data) {
		//LOGGER.info(String.format(data.getPlayerMove()) + String.format(data.getOpponentMove()) + String.format(data.getResult()));
		
		rpsGameRepository.save(data);
		//rpsGameService.saveGameHistory(data);
	}
	
}
