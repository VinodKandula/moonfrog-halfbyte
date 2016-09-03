/**
 * 
 */
package com.aws.sns.service.notifications;

/**
 * @author Vinod
 *
 */
public enum NotificationTypeEnum {
	WEEKLY(1),
	COMMON(2),
	DAILY(3),
	RECOMMENDED(4);

	private int ntid;
	
	NotificationTypeEnum(int ntid) {
		this.ntid = ntid;
	}
	
	public int getNotificationTypeId() {
		return this.ntid;
	}
	
}
