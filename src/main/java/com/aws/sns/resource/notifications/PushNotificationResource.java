/**
 * 
 */
package com.aws.sns.resource.notifications;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.aws.sns.dto.NotificationsHistoryDTO;
import com.aws.sns.service.notifications.PushNotificationService;
import com.aws.sns.service.queue.message.RecommendedNotificationMessage;

/**
 * @author Vinod
 *
 */
@Path("pushNotification")
public class PushNotificationResource {
	
	PushNotificationService pushNotificationService =  new PushNotificationService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}/viewed")
	public void createNotificationsHistory(@PathParam("deviceUUID") String deviceUUID, String action) {
		NotificationsHistoryDTO historyDTO = new NotificationsHistoryDTO(); 
		historyDTO.setDeviceUUID(deviceUUID);
		historyDTO.setAction(action);
		pushNotificationService.createNotificationsHistory(historyDTO);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("recommendedPushNotification")
	public void sendRecommendedPushNotification(RecommendedNotificationMessage message) {
		
	}

}
