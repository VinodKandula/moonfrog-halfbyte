/**
 * 
 */
package com.aws.sns.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vinod
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NotificationStatsSummary {
	
	@XmlElement(required=true) 
	protected int notificationCount;

	public int getNotificationCount() {
		return notificationCount;
	}

	public void setNotificationCount(int notificationCount) {
		this.notificationCount = notificationCount;
	}
	
}
