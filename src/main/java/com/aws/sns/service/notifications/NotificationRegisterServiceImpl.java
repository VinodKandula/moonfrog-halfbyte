/**
 * 
 */
package com.aws.sns.service.notifications;

import com.aws.sns.dao.notifications.APNSUserDAO;
import com.aws.sns.dao.notifications.GCMUserDAO;
import com.aws.sns.util.StringUtil;

/**
 * @author Vinod
 *
 */
public class NotificationRegisterServiceImpl {

	GCMUserDAO gcmUsersDAO = new GCMUserDAO();
	APNSUserDAO apnsUsersDAO = new APNSUserDAO();

	public String getRegIdByDeviceUUID(String deviceUUID) {
		return gcmUsersDAO.getRegIdByDeviceUUID(deviceUUID);
	}

	public void registerGCMUser(String deviceUUID, String gcmRegId) {
		if (StringUtil.isNotEmpty(deviceUUID, gcmRegId)) {
			unRegisterGCMUser(deviceUUID);
			/*
			 * long guid = gcmUsersDAO.getGCMUser(deviceUUID, gcmRegId); if
			 * (guid > 0) { gcmUsersDAO.updateGCMUser(deviceUUID, gcmRegId); }
			 * else { gcmUsersDAO.registerGCMUser(deviceUUID, gcmRegId); }
			 */
			gcmUsersDAO.registerGCMUser(deviceUUID, gcmRegId);
		}
	}

	public void activateGCMUser(String deviceUUID) {
		gcmUsersDAO.activateGCMUser(deviceUUID);
	}

	public void unRegisterGCMUser(String deviceUUID) {
		gcmUsersDAO.unRegisterGCMUser(deviceUUID);
	}

	public void registerAPNSUser(String deviceUUID, String apnsTokenId) {
		if (StringUtil.isNotEmpty(deviceUUID, apnsTokenId)) {
			unRegisterAPNSUser(deviceUUID);
			apnsUsersDAO.registerAPNSUser(deviceUUID, apnsTokenId);
		}
	}

	public void activateAPNSUser(String deviceUUID) {
		apnsUsersDAO.activateAPNSUser(deviceUUID);
	}

	public void unRegisterAPNSUser(String deviceUUID) {
		apnsUsersDAO.unRegisterAPNSUser(deviceUUID);
	}
}
