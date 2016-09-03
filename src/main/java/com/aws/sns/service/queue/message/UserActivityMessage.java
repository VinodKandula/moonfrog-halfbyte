package com.aws.sns.service.queue.message;

import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author vinod
 *
 */
@XmlRootElement
public class UserActivityMessage implements IMessage {
	
	private String gameId;
	private String gameName;
	private String event;
	private List<String> subEvents;
	private Timestamp timestamp;
	private long score;
	private String deviceUUID;
	
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
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public List<String> getSubEvents() {
		return subEvents;
	}
	public void setSubEvents(List<String> subEvents) {
		this.subEvents = subEvents;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public String getDeviceUUID() {
		return deviceUUID;
	}
	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}
	
	@Override
	public String toString() {
		return "UserActivityMessage [gameId=" + gameId + ", gameName=" + gameName + ", event=" + event + ", subEvents="
				+ subEvents + ", timestamp=" + timestamp + ", score=" + score + ", deviceUUID=" + deviceUUID + "]";
	}

	
}
