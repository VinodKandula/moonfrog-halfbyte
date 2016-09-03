/**
 * 
 */
package com.aws.sns.service.notifications.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.dao.notifications.APNSUserDAO;
import com.aws.sns.dao.notifications.GCMUserDAO;
import com.aws.sns.dto.PushNotificationUserDTO;
import com.aws.sns.service.notifications.NotificationTypeEnum;
import com.aws.sns.service.notifications.sns.SNSPlatformHelper;
import com.aws.sns.service.queue.NamedThreadFactory;

/**
 * @author Vinod
 *
 */
public class NotificationExecutorService {
	private static final Log LOGGER = LogFactory.getLog(NotificationExecutorService.class);
	
	public void performWeeklyBulkNotification() {
		try {
			//TODO partition the users
			// use partition strategies to scale on different nodes
			
			//pull the partition from DB and workers threads work on the given partition
			//get all active users
			List<PushNotificationUserDTO> gcmUserDTOs = new GCMUserDAO().getAllActiveUsers();
			List<PushNotificationUserDTO> apnsUserDTOs = new APNSUserDAO().getAllActiveUsers();
			
			int defaultNumberOfThreads = 100;
			
			int numberOfThreads = Math.min(defaultNumberOfThreads, gcmUserDTOs.size() + apnsUserDTOs.size());
			LOGGER.info("No of threads : "+numberOfThreads);
			ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads, new NamedThreadFactory("BulkWeeklyTaskExeSvc"));
			List<INotificationTaskWorker> notificationTaskWorkers = new ArrayList<INotificationTaskWorker>();
			
			for (PushNotificationUserDTO gcmUserDTO : gcmUserDTOs) {
				//SNSMobilePushTask worker = new SNSMobilePushTask(SNSPlatformHelper.Platform.GCM, nottificationType, gcmUserDTO.getDeviceUUID(), gcmUserDTO.getRegId(), messageText); 
				//executor.execute(worker);
				NotificationTaskWorkerInput input = new NotificationTaskWorkerInput();
				input.setDeviceUUID(gcmUserDTO.getDeviceUUID());
				input.setToken(gcmUserDTO.getRegId());
				input.setPlatform(SNSPlatformHelper.Platform.GCM);
				input.setNotificationTypeEnum(NotificationTypeEnum.WEEKLY);
				input.setAction(INotificationTaskWorker.COMMON_ACTION);
				input.setCollapseKey(NotificationTypeEnum.WEEKLY.name());
				INotificationTaskWorker taskWorker = new WeeklyNotificationTaskWorker(input);
				notificationTaskWorkers.add(taskWorker);
			}
			
			for (PushNotificationUserDTO apnsUserDTO : apnsUserDTOs) {
				NotificationTaskWorkerInput input = new NotificationTaskWorkerInput();
				input.setDeviceUUID(apnsUserDTO.getDeviceUUID());
				input.setToken(apnsUserDTO.getRegId());
				input.setPlatform(SNSPlatformHelper.Platform.APNS);
				input.setNotificationTypeEnum(NotificationTypeEnum.WEEKLY);
				input.setAction(INotificationTaskWorker.COMMON_ACTION);
				input.setCollapseKey(NotificationTypeEnum.WEEKLY.name());
				INotificationTaskWorker taskWorker = new WeeklyNotificationTaskWorker(input);
				notificationTaskWorkers.add(taskWorker);
			}
			
			List<Future<NotificationTaskWorkerInfo>> results = executor.invokeAll(notificationTaskWorkers);
			boolean overallStatus = true;
			for (Future<NotificationTaskWorkerInfo> result : results) {
				if(result.isDone()) {
					NotificationTaskWorkerInfo bean = result.get();
					overallStatus = overallStatus && bean.getExitCode() == 1;
				} else {
					overallStatus = false;
				}
			}
			executor.shutdown();
		} catch (Exception e) {
			LOGGER.info("Exception in performWeeklyNotification : "+e);
		} 
	}
	
	public void performDailyNotification() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new NotificationExecutorService().performWeeklyBulkNotification();

	}

}
