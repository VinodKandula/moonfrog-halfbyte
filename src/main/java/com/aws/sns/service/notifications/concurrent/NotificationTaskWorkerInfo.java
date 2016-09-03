/**
 * 
 */
package com.aws.sns.service.notifications.concurrent;

import java.util.Date;

/**
 * @author Vinod
 *
 */
public class NotificationTaskWorkerInfo {

	private String deviceUUID;
	private String token;
	private String taskName;
	private int exitCode;
	private Date startTime;
	private Date endTime;
	private String message;
	private String eventType;
	
	public String getDeviceUUID() {
		return deviceUUID;
	}
	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getExitCode() {
		return exitCode;
	}
	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	@Override
	public String toString() {
		return "NotificationTaskWorkerInfo [deviceUUID=" + deviceUUID
				+ ", token=" + token + ", taskName=" + taskName + ", exitCode="
				+ exitCode + ", startTime=" + startTime + ", endTime="
				+ endTime + ", message=" + message + ", eventType=" + eventType
				+ "]";
	}
}
