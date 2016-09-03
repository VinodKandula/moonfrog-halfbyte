/**
 * 
 */
package com.aws.sns.service.notifications.concurrent;

import javax.xml.bind.annotation.XmlRootElement;

import com.aws.sns.service.notifications.NotificationTypeEnum;
import com.aws.sns.service.notifications.sns.SNSPlatformHelper;

/**
 * @author Vinod
 *
 */
@XmlRootElement
public class NotificationTaskWorkerInput {

	private String deviceUUID;
	private String token;
	private SNSPlatformHelper.Platform platform;
	private NotificationTypeEnum notificationTypeEnum;
	private String message;
	private String action;
	private String collapseKey;
	
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
	public SNSPlatformHelper.Platform getPlatform() {
		return platform;
	}
	public void setPlatform(SNSPlatformHelper.Platform platform) {
		this.platform = platform;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public NotificationTypeEnum getNotificationTypeEnum() {
		return notificationTypeEnum;
	}
	public void setNotificationTypeEnum(NotificationTypeEnum notificationTypeEnum) {
		this.notificationTypeEnum = notificationTypeEnum;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCollapseKey() {
		return collapseKey;
	}
	public void setCollapseKey(String collapseKey) {
		this.collapseKey = collapseKey;
	}
	@Override
	public String toString() {
		return "NotificationTaskWorkerInput [deviceUUID=" + deviceUUID
				+ ", token=" + token + ", platform=" + platform
				+ ", notificationTypeEnum=" + notificationTypeEnum
				+ ", message=" + message + ", action=" + action + "]";
	}

}
