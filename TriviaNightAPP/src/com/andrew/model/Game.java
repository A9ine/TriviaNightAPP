package com.andrew.model;

public class Game {
	int gameID;
	String gameName;
	String gameStartTime;
	String gameEndTIme;
	int hasGameStarted;
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameStartTime() {
		return gameStartTime;
	}
	public void setGameStartTime(String gameStartTime) {
		this.gameStartTime = gameStartTime;
	}
	public String getGameEndTIme() {
		return gameEndTIme;
	}
	public void setGameEndTIme(String gameEndTIme) {
		this.gameEndTIme = gameEndTIme;
	}
	public int getHasGameStarted() {
		return hasGameStarted;
	}
	public void setHasGameStarted(int hasGameStarted) {
		this.hasGameStarted = hasGameStarted;
	}
}




