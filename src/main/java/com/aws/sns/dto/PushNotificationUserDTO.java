/**
 * 
 */
package com.aws.sns.dto;

/**
 * @author Vinod
 *
 */
public class PushNotificationUserDTO {

	private String deviceUUID;
	private String regId;
	
	public String getDeviceUUID() {
		return deviceUUID;
	}
	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
}
