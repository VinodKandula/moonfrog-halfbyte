/**
 * 
 */
package com.aws.sns.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.service.UserAuthServiceImpl;

/**
 * @author Vinod
 *
 */
@Path("userAuth")
public class UserAuthResource {
	private static final Log LOGGER = LogFactory.getLog(UserAuthResource.class);

	UserAuthServiceImpl userAuthServiceImpl;

	public UserAuthResource() {
		userAuthServiceImpl = new UserAuthServiceImpl();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}/createPassword")
	public void createUserPassword(@PathParam("deviceUUID") String deviceUUID, String password) {
		// userAuthServiceImpl.createUserPassword(deviceUUID, password);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}/otp")
	public String getUserOTP(@PathParam("deviceUUID") String deviceUUID) {
		return userAuthServiceImpl.getUserOTP(deviceUUID);
	}

}
