/**
 * 
 */
package com.aws.sns.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.aws.sns.service.UserActivityServiceImpl;
import com.aws.sns.service.queue.message.UserActivityMessage;

/**
 * @author vinod
 *
 */
@Path("userActivity")
public class UserActivityResource {

	UserActivityServiceImpl userActivityServiceImpl;
	public UserActivityResource() {
		userActivityServiceImpl = new UserActivityServiceImpl();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}")
	public void createUserActivity(@PathParam("deviceUUID") String deviceUUID, UserActivityMessage userActivityMessage) {
		userActivityMessage.setDeviceUUID(deviceUUID);
		userActivityServiceImpl.createUserActivity(userActivityMessage);
	}
}
