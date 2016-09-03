/**
 * 
 */
package com.aws.sns.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aws.sns.common.exception.MoonfrogApplicationException;
import com.aws.sns.dto.UserDTO;
import com.aws.sns.dto.UserDemoProfileDTO;
import com.aws.sns.service.UserActivityServiceImpl;
import com.aws.sns.service.UserServiceImpl;


/**
 * @author Vinod
 *
 */
@Path("users")
public class UserResource {
	
	UserServiceImpl userServiceImpl;
	UserActivityServiceImpl userActivityServiceImpl;
	public UserResource() {
		userServiceImpl = new UserServiceImpl();
		userActivityServiceImpl = new UserActivityServiceImpl();
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> getUsers() {
		return userServiceImpl.getUsers();
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{deviceUUID}/userDemoProfile")
    public UserDTO getUserDemoProfile(@PathParam("deviceUUID") String deviceUUID) throws MoonfrogApplicationException {
		return userServiceImpl.getUserDemoProfile(deviceUUID);
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}/userDemoProfile")
	public void createUpdateUserDemoProfile(@PathParam("deviceUUID") String deviceUUID,UserDemoProfileDTO userDemoProfileDTO) {
		userDemoProfileDTO.setDeviceUUID(deviceUUID);
		userServiceImpl.createUpdateUserDemoProfile(userDemoProfileDTO);
	}
	
}
