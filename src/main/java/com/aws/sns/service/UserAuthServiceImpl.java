/**
 * 
 */
package com.aws.sns.service;

import com.aws.sns.dao.UserAuthDAO;

/**
 * @author Vinod
 *
 */
public class UserAuthServiceImpl {
	
	UserAuthDAO userAuthDAO;
	public UserAuthServiceImpl() {
		userAuthDAO = new UserAuthDAO(); 
	}
	
	public String getUserOTP(String deviceUUID) {
		return userAuthDAO.getUserOTP(deviceUUID);
	}
}
