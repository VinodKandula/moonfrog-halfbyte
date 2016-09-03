/**
 * 
 */
package com.aws.sns.service.notifications.concurrent;

import java.util.concurrent.Callable;

/**
 * @author Vinod
 *
 */
public interface INotificationTaskWorker extends Callable<NotificationTaskWorkerInfo> {

	public static final String SUCCESS = "SUCCESS";
	
	public static final String FAIL = "FAIL";
	
	public static final int SUCCESS_EXIT_CODE = 1;
	
	public static final int FAIL_EXIT_CODE = -1;
	
	public static String NOTIFICATION_TXT_TEMPLATE = "Moonfrog HalfByte Team";
		
	public static String COMMON_ACTION =  "your app page uri";
	
	public NotificationTaskWorkerInfo call() throws Exception;
}
