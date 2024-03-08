package com.example.RPSgame;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import jakarta.persistence.TypedQuery;

@Service
public class RpsGameService {
	@Autowired
	private RpsGameRepository rpsGameRepository;
	
	@Autowired
	private RpsGameProducerService producer;
	
	public RpsGame getResult(String playerMove) {
		String opponentMove = getOpponentMove();
		String result = determineResult(playerMove, opponentMove);
		
		RpsGame gameResult = new RpsGame();
	    gameResult.setPlayerMove(playerMove);
	    gameResult.setOpponentMove(opponentMove);
	    gameResult.setResult(result);
	    gameResult.setTimestamp(LocalDateTime.now());
	    
	    producer.sendMessage(gameResult);
		
		return gameResult;
	}
	
	@Cacheable(value = "gameHistoryCache")
	public List<RpsGame> getGameHistory(){
		return rpsGameRepository.findAll();
	}
	
	public List<RpsGame> getScore(){
		return rpsGameRepository.findAll();
	}
	
	public HttpHeaders allowCORS() {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return headers;
	}
	
	@CacheEvict(value = "gameHistoryCache", allEntries = true)
	public void deleteHistory() {
		rpsGameRepository.deleteAll();
	}
	
	private String getOpponentMove() {
		String[] move = {"rock", "paper", "scissors"};
		int random = (int)(Math.random() * move.length);
		return move[random];
	}
	
	private String determineResult(String playerMove, String opponentMove) {
		if (playerMove.equals(opponentMove)) {
            return "Tie";
        } else if (
                (playerMove.equals("rock") && opponentMove.equals("scissors")) ||
                (playerMove.equals("paper") && opponentMove.equals("rock")) ||
                (playerMove.equals("scissors") && opponentMove.equals("paper"))
        ) {
            return "You Win";
        } else {
            return "You Lose";
        }
	}
	
	public void saveGameHistory(RpsGame game) {
		//System.out.print("service--->" + game.getPlayerMove() + game.getOpponentMove() + game.getResult());
		rpsGameRepository.save(game);
	}
}
