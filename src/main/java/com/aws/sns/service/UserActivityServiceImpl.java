/**
 * 
 */
package com.aws.sns.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.service.queue.AsyncMessageQueueUtil;
import com.aws.sns.service.queue.message.IMessage;
import com.aws.sns.service.queue.message.UserActivityMessage;

/**
 * @author Vinod
 *
 */
public class UserActivityServiceImpl {
	private static final Log LOGGER = LogFactory.getLog(UserActivityServiceImpl.class);
	private static final boolean activityAsyncFlag = true;
	
	public void createUserActivity(UserActivityMessage message) {
		this.createUserActivity(message, activityAsyncFlag);
	}

	public void createUserActivity(IMessage message, boolean activityAsyncFlag) {
		if (activityAsyncFlag) {
			AsyncMessageQueueUtil.enQueueMessage(message);
		} else {
			// directly update DB
		}
	}
	
}
