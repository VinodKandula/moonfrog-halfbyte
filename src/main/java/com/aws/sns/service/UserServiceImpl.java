/**
 * 
 */
package com.aws.sns.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.dao.UserDAO;
import com.aws.sns.dto.UserDTO;
import com.aws.sns.dto.UserDemoProfileDTO;

/**
 * @author Vinod
 *
 */
public class UserServiceImpl {
	private static final Log LOGGER = LogFactory.getLog(UserServiceImpl.class);
	
	UserDAO userDAO;
	UserActivityServiceImpl userActivityServiceImpl;
	public UserServiceImpl() {
		userDAO = new UserDAO();
		userActivityServiceImpl = new UserActivityServiceImpl();
	}
	
	public List<Integer> getUserCategories(String deviceUUID) {
		return userDAO.getUserCategories(deviceUUID);
	}
	
	public List<UserDTO> getUsers() {
		return userDAO.getUsers();
	}
	
	public UserDemoProfileDTO getUserDemoProfiles(String deviceUUID) {
		return userDAO.getUserDemoProfiles(deviceUUID);
	}
	
	public UserDemoProfileDTO getUserDemoProfile(String deviceUUID) {
		UserDemoProfileDTO userDemoProfileDTO = userDAO.getUserDemoProfiles(deviceUUID);
		return userDemoProfileDTO == null ? userDAO.getUserByOTP(deviceUUID) : userDemoProfileDTO;
	}
	
	public void createUpdateUserDemoProfile(UserDemoProfileDTO userDemoProfileDTO) {
		UserDemoProfileDTO userDemo = userDAO.getUserDemoProfiles(userDemoProfileDTO.getDeviceUUID(), userDemoProfileDTO.getLoginId());
		if (userDemo == null ) {
			userDAO.createUserDemoProfile(userDemoProfileDTO);
		} else {
			userDemoProfileDTO.setUdid(userDemo.getUdid());
			userDemoProfileDTO.setUid(userDemo.getUid());
			userDAO.updateUserDemoProfile(userDemoProfileDTO);
		}
	}

	public void createUser(String deviceUUID) {
		long uid  = userDAO.getUserIdByDeviceUUID(deviceUUID);
		
		if (uid == -1) {
			// create new user
			userDAO.createUserByDeviceUUID(deviceUUID);
		} else {
			// ignore
		}
	}
}
