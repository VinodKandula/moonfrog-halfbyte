package com.aws.sns.mongo.dto;

import java.util.List;

public class UserActivity {
	
	private String userUUID;
	
	private String gameId;
	
	private String gameName;
	
	private String event;
	
	private List<String> subEvents;
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	private long timestamp;
	
	private long score;

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public List<String> getSubEvents() {
		return subEvents;
	}

	public void setSubEvents(List<String> subEvents) {
		this.subEvents = subEvents;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
	
}
