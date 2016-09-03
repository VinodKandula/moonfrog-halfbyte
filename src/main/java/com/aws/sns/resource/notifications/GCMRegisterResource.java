/**
 * 
 */
package com.aws.sns.resource.notifications;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.aws.sns.service.notifications.NotificationRegisterServiceImpl;

/**
 * @author Vinod
 *
 */
@Path("gcm")
public class GCMRegisterResource {

	NotificationRegisterServiceImpl notificationRegisterServiceImpl = new NotificationRegisterServiceImpl();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}")
	public void registerGCMUser(@PathParam("deviceUUID") String deviceUUID,String gcmTokenId) {
		notificationRegisterServiceImpl.registerGCMUser(deviceUUID, gcmTokenId);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}")
	public void activateGCMUser(@PathParam("deviceUUID") String deviceUUID) {
		notificationRegisterServiceImpl.activateGCMUser(deviceUUID);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}")
	public void unRegisterGCMUser(@PathParam("deviceUUID") String deviceUUID) {
		notificationRegisterServiceImpl.unRegisterGCMUser(deviceUUID);
	}
	
}
