/**
 * 
 */
package com.aws.sns.service.notifications.concurrent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.dao.notifications.NotificationDAO;
import com.aws.sns.dto.NotificationsHistoryDTO;
import com.aws.sns.service.notifications.NotificationTypeEnum;
import com.aws.sns.service.notifications.NotificationsHistoryTypeEnum;
import com.aws.sns.service.notifications.sns.SNSMobilePush;

/**
 * @author Vinod
 *
 */
public class WeeklyNotificationTaskWorker implements INotificationTaskWorker {
	private static final Log LOGGER = LogFactory.getLog(WeeklyNotificationTaskWorker.class);
	
	private NotificationTaskWorkerInput input;
	
	public WeeklyNotificationTaskWorker(NotificationTaskWorkerInput input) {
		this.input = input;
	}

	@Override
	public NotificationTaskWorkerInfo call() throws Exception {
		NotificationTaskWorkerInfo taskWorkerInfo = new NotificationTaskWorkerInfo();
		taskWorkerInfo.setExitCode(FAIL_EXIT_CODE);
		taskWorkerInfo.setDeviceUUID(input.getDeviceUUID());
		taskWorkerInfo.setToken(input.getToken());
		try {
			/*String messageText = NOTIFICATION_TXT_TEMPLATE.replace("#1", ""+count);
			LOGGER.info(messageText);*/
			LOGGER.info("User DEVICE UUID : "+input.getDeviceUUID());
			LOGGER.info("Reg Id : "+input.getToken());
			input.setMessage(NOTIFICATION_TXT_TEMPLATE);
			SNSMobilePush.sendPushNotifications(input);
			taskWorkerInfo.setExitCode(SUCCESS_EXIT_CODE);
			
			// notification history
			NotificationsHistoryDTO notificationsHistory = new NotificationsHistoryDTO();
			notificationsHistory.setDeviceUUID(input.getDeviceUUID());
			notificationsHistory.setNtid(NotificationTypeEnum.WEEKLY.getNotificationTypeId());
			notificationsHistory.setNhtid(NotificationsHistoryTypeEnum.NOTIFICATION_SENT.getNotificationsHistoryTypeId());
			notificationsHistory.setAction(input.getAction());
			new NotificationDAO().createNotificationsHistory(notificationsHistory);

		} catch (Exception e) {
			taskWorkerInfo.setExitCode(FAIL_EXIT_CODE);
			taskWorkerInfo.setMessage(FAIL);
			LOGGER.error("Error in WeeklyNotificationTaskWorker : "+e);
		}
		
		return taskWorkerInfo;
	}

}
