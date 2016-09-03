/**
 * 
 */
package com.aws.sns.dto;

/**
 * @author Vinod
 *
 */
public class NotificationsHistoryDTO {
	
	private long nhid;
    private String deviceUUID;
    private int ntid;
    private int nhtid;
    private String action;
    
	public long getNhid() {
		return nhid;
	}
	public void setNhid(long nhid) {
		this.nhid = nhid;
	}
	public String getDeviceUUID() {
		return deviceUUID;
	}
	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}
	public int getNtid() {
		return ntid;
	}
	public void setNtid(int ntid) {
		this.ntid = ntid;
	}
	public int getNhtid() {
		return nhtid;
	}
	public void setNhtid(int nhtid) {
		this.nhtid = nhtid;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

}
