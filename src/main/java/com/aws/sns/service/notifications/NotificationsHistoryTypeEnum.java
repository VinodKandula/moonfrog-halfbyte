/**
 * 
 */
package com.aws.sns.service.notifications;

/**
 * @author Vinod
 *
 */
public enum NotificationsHistoryTypeEnum {
	NOTIFICATION_SENT(1),
	NOTIFICATION_CLICKED(2);

	private int nhtid;
	
	NotificationsHistoryTypeEnum(int nhtid) {
		this.nhtid = nhtid;
	}
	
	public int getNotificationsHistoryTypeId() {
		return this.nhtid;
	}
	
}
