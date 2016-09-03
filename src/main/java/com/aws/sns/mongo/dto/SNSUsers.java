package com.aws.sns.mongo.dto;

public class SNSUsers {
	
	private String snsRegId;
	
	private String deviceUUID;
	
	private String snsPlatform;
	
	private String status;
	
	private long created;
	
	private long updated;

	public String getSnsRegId() {
		return snsRegId;
	}

	public void setSnsRegId(String snsRegId) {
		this.snsRegId = snsRegId;
	}

	public String getDeviceUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = System.currentTimeMillis();
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = System.currentTimeMillis();
	}

	public String getSnsPlatform() {
		return snsPlatform;
	}

	public void setSnsPlatform(String snsPlatform) {
		this.snsPlatform = snsPlatform;
	}

}
