package com.example.RPSgame;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class RpsGameService {
	@Autowired
	private RpsGameRepository rpsGameRepository;
	
	@Autowired
	private RpsGameProducerService producer;
	
	public RpsGame getResult(String playerMove) {
		String opponentMove = getOpponentMove();
		String result = determineResult(playerMove, opponentMove);
		//saveGameHistory(playerMove, opponentMove, result);
		
		RpsGame gameResult = new RpsGame();
	    gameResult.setPlayerMove(playerMove);
	    gameResult.setOpponentMove(opponentMove);
	    gameResult.setResult(result);
	    gameResult.setTimestamp(LocalDateTime.now());
	    
	    producer.sendMessage(gameResult);
		
		return gameResult;
	}
	
	public List<RpsGame> getGameHistory(){
		return rpsGameRepository.findAll();
	}
	
	public HttpHeaders allowCORS() {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return headers;
	}
	
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
	
	private void saveGameHistory(String playerMove, String opponentMove, String result) {
		RpsGame game = new RpsGame();
		game.setPlayerMove(playerMove);
	    game.setOpponentMove(opponentMove);
	    game.setResult(result);
	    game.setTimestamp(LocalDateTime.now());
		rpsGameRepository.save(game);
	}
}
