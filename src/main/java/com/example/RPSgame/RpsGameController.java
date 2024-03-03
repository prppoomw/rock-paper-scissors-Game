package com.example.RPSgame;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("/api/rps-game")
public class RpsGameController {
	@Autowired
	private RpsGameService rpsGameService;
	
	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
	}
	
	@PostMapping("/play")
	public ResponseEntity<RpsGame> play(@RequestBody String playerMove){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(playerMove);
	        String playerMoveValue = jsonNode.get("playerMove").asText();
//	        System.out.println(playerMoveValue);
	        
	        return ResponseEntity.ok()
					.headers(rpsGameService.allowCORS())
					.body(rpsGameService.getResult(playerMoveValue));
        }
		catch (JsonProcessingException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	}
	
	@GetMapping("/history")
	public List<RpsGame> gameHistory(){
		return rpsGameService.getGameHistory();
	}
	
	@DeleteMapping("/reset")
	public ResponseEntity<String> reset(){
		rpsGameService.deleteHistory();
		return ResponseEntity.ok()
				.headers(rpsGameService.allowCORS())
				.body("Game history reset successfully");
	}
}
