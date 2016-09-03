/**
 * 
 */
package com.aws.sns.service.notifications;

import com.aws.sns.dao.notifications.APNSUserDAO;
import com.aws.sns.dao.notifications.GCMUserDAO;
import com.aws.sns.dao.notifications.NotificationDAO;
import com.aws.sns.dto.NotificationsHistoryDTO;
import com.aws.sns.service.notifications.concurrent.NotificationTaskWorkerInput;
import com.aws.sns.service.notifications.sns.SNSMobilePush;
import com.aws.sns.service.notifications.sns.SNSPlatformHelper;
import com.aws.sns.service.queue.AsyncMessageQueueUtil;
import com.aws.sns.service.queue.message.RecommendedNotificationMessage;

/**
 * @author Vinod
 *
 */
public class PushNotificationService {
	
	GCMUserDAO gcmUsersDAO;
	APNSUserDAO apnsUsersDAO;
	NotificationDAO notificationDAO;

	public PushNotificationService() {
		gcmUsersDAO = new GCMUserDAO();
		apnsUsersDAO = new APNSUserDAO();
		notificationDAO = new NotificationDAO();
	}

	public void sendRecommendedPushNotification(RecommendedNotificationMessage inputMessage) {
		AsyncMessageQueueUtil.enQueueMessage(inputMessage);
	}

	@Deprecated
	public class AsyncThread implements Runnable {
		SNSPlatformHelper.Platform platform;
		String regId;
		String messageText;

		AsyncThread(SNSPlatformHelper.Platform platform, String regId, String messageText) {
			this.platform = platform;
			this.regId = regId;
			this.messageText = messageText;
		}

		@Override
		public void run() {
			NotificationTaskWorkerInput taskWorkerInput = new NotificationTaskWorkerInput();
			taskWorkerInput.setPlatform(SNSPlatformHelper.Platform.GCM);
			taskWorkerInput.setToken(regId);
			taskWorkerInput.setMessage(messageText);
			SNSMobilePush.sendPushNotifications(taskWorkerInput);
		}
	}

	
	public void createNotificationsHistory(NotificationsHistoryDTO historyDTO) {
		historyDTO.setNtid(NotificationTypeEnum.COMMON.getNotificationTypeId());
		historyDTO.setNhtid(NotificationsHistoryTypeEnum.NOTIFICATION_CLICKED.getNotificationsHistoryTypeId());
		notificationDAO.createNotificationsHistory(historyDTO);
	}
}
