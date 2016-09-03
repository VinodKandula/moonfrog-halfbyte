/**
 * 
 */
package com.aws.sns.service.queue;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.dao.UserDAO;
import com.aws.sns.dao.notifications.NotificationDAO;
import com.aws.sns.dto.NotificationsHistoryDTO;
import com.aws.sns.service.UserActivityServiceImpl;
import com.aws.sns.service.notifications.NotificationTypeEnum;
import com.aws.sns.service.notifications.NotificationsHistoryTypeEnum;
import com.aws.sns.service.notifications.sns.SNSMobilePush;
import com.aws.sns.service.queue.message.IMessage;
import com.aws.sns.service.queue.message.RecommendedNotificationMessage;
import com.aws.sns.service.queue.message.UserActivityMessage;

/**
 * @author Vinod
 *
 */
public class AsyncBlockingConsumer implements Runnable {
	private static final Log LOGGER = LogFactory.getLog(AsyncBlockingConsumer.class);
	
	private static AtomicBoolean SHUTDOWN = new AtomicBoolean(false);

	private final BlockingQueue<IMessage> queue;
	private volatile boolean stopConsuming = false;
	private UserDAO userDAO = new UserDAO();
	private NotificationDAO notificationDAO = new NotificationDAO();
	private UserActivityServiceImpl userActivityServiceImpl = new UserActivityServiceImpl();

	AsyncBlockingConsumer(BlockingQueue<IMessage> q) {
		queue = q;
	}

	public void run() {
		try {
			while (true) {
				IMessage objectFromQueue = queue.poll();
				/**
				 * The non-blocking poll() method returns null if the queue is
				 * empty
				 */
				if (objectFromQueue == null) {
					long start = System.currentTimeMillis();
					/**
					 * Now use the blocking take() method which can wait for the
					 * object to be available in queue.
					 */
					objectFromQueue = queue.take();
					LOGGER.info(Thread.currentThread().getName()+ " It seems Producer is slow. Consumer waited for "
									+ (System.currentTimeMillis() - start)
									+ "ms");
				}
				if (objectFromQueue != null) {
					consume(objectFromQueue);
				}
				if (SHUTDOWN.get()) {
					// Thread is getting ready to die, but first,
			        // drain remaining elements on the queue and process them.
			        final LinkedList<IMessage> remainingObjects = new LinkedList<>();
			        this.queue.drainTo(remainingObjects);
			        for(IMessage data : remainingObjects) {
			            this.consume(data);
			        }
			        LOGGER.info("#### Stopping Thread :"+Thread.currentThread().getName());
					break;
				}
			}
		} catch (InterruptedException ex) {
			LOGGER.error("Error in AsyncBlockingConsumer : "+ex);
		}
	}

	void consume(IMessage message) {
		LOGGER.info(Thread.currentThread().getName()+ " Async Consumer start ...");
		try {
			if (message instanceof UserActivityMessage) {
				UserActivityMessage userActivityMessage = (UserActivityMessage) message;
				//TODO update to db & run rules to send personalized push notifications (may be self & other users as well)
				LOGGER.info("***Data : "+userActivityMessage.toString());
			} else if (message instanceof RecommendedNotificationMessage) {
				RecommendedNotificationMessage input = (RecommendedNotificationMessage) message;
				SNSMobilePush.sendPushNotifications(input);
				
				// notification history
				NotificationsHistoryDTO notificationsHistory = new NotificationsHistoryDTO();
				notificationsHistory.setDeviceUUID(input.getDeviceUUID());
				notificationsHistory.setNtid(NotificationTypeEnum.WEEKLY.getNotificationTypeId());
				notificationsHistory.setNhtid(NotificationsHistoryTypeEnum.NOTIFICATION_SENT.getNotificationsHistoryTypeId());
				notificationsHistory.setAction(input.getAction());
				new NotificationDAO().createNotificationsHistory(notificationsHistory);
				
			}
		} catch (Throwable t) {
			LOGGER.error("Error in AsyncBlockingConsumer : "+t);
		}
	}

	public void shutdown() {
		SHUTDOWN.set(true);
	}
}
