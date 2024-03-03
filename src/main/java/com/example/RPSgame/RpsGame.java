package com.example.RPSgame;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="rps-game-history")
public class RpsGame {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String playermove;
	private String opponentmove;
	private String result;
	private LocalDateTime timestamp;
	
	public RpsGame() {
		super();
	}
	
	public RpsGame(Integer id, String playerMove, String opponentMove, String result, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.playermove = playerMove;
		this.opponentmove = opponentMove;
		this.result = result;
		this.timestamp = timestamp;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPlayerMove() {
		return this.playermove;
	}

	public void setPlayerMove(String playerMove) {
		this.playermove = playerMove;
	}
	
	public String getOpponentMove() {
		return this.opponentmove;
	}
	
	public void setOpponentMove(String opponentMove) {
		this.opponentmove = opponentMove;
	}
	
	public String getResult() {
		return this.result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
