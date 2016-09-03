package com.aws.sns.resource.notifications;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.aws.sns.service.notifications.NotificationRegisterServiceImpl;


@Path("apns")
public class APNSRegisterResource {

	NotificationRegisterServiceImpl notificationRegisterServiceImpl = new NotificationRegisterServiceImpl();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}")
	public void registerAPNSUser(@PathParam("deviceUUID") String deviceUUID,String apnsTokenId) {
		notificationRegisterServiceImpl.registerAPNSUser(deviceUUID, apnsTokenId);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}")
	public void activateAPNSUser(@PathParam("deviceUUID") String deviceUUID) {
		notificationRegisterServiceImpl.activateAPNSUser(deviceUUID);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}")
	public void unRegisterAPNSUser(@PathParam("deviceUUID") String deviceUUID) {
		notificationRegisterServiceImpl.unRegisterAPNSUser(deviceUUID);
	}
	
}
