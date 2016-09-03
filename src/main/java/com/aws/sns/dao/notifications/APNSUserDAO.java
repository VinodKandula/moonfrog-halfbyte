package com.aws.sns.dao.notifications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.common.StatusEnum;
import com.aws.sns.dao.DBConnector;
import com.aws.sns.dto.PushNotificationUserDTO;

public class APNSUserDAO {

	private static final Log LOGGER = LogFactory.getLog(APNSUserDAO.class);
	
	private static String APNS_USER_U_SQL = "UPDATE `apns_users` SET `STATUS` = ?,UPDATE_TS = CURRENT_TIMESTAMP WHERE `DEVICE_UUID`=?";
	private static String APNS_USER_I_SQL = "INSERT INTO `apns_users` (`APNS_REGID`, `DEVICE_UUID`, `STATUS`) VALUES (?, ?, ?)";
	private static String APNS_ACTIVE_USER_S_SQL = "select APNS_REGID,DEVICE_UUID from apns_users where STATUS='A'";
	private static String APNS_USER_REGID_S_SQL = "select APNS_REGID from apns_users where DEVICE_UUID=? and STATUS = ?";
	
	public void registerAPNSUser(String deviceUUID, String tokenId) {
		this.unRegisterAPNSUser(deviceUUID);
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(APNS_USER_I_SQL);
			pstmt.setString(1, tokenId);
			pstmt.setString(2, deviceUUID);
			pstmt.setString(3, tokenId == null ? StatusEnum.I.toString()
					: StatusEnum.A.toString());
			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in registerAPNSUser :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public void activateAPNSUser(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(APNS_USER_U_SQL);
			pstmt.setString(1, StatusEnum.A.toString());
			pstmt.setString(2, deviceUUID);

			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in activateAPNSUser :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public void unRegisterAPNSUser(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(APNS_USER_U_SQL);
			pstmt.setString(1, StatusEnum.I.toString());
			pstmt.setString(2, deviceUUID);

			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in unRegisterAPNSUser :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}
	
	public String getRegIdByDeviceUUID(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String regId = null;
		try {
			pstmt = conn.prepareStatement(APNS_USER_REGID_S_SQL);
			pstmt.setString(1, deviceUUID);
			pstmt.setString(2, StatusEnum.A.name());
			LOGGER.info(pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				regId = rs.getString(1);
			}
		} catch (Exception e) {
			LOGGER.error("Error in getRegIdByDeviceUUID :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return regId;
	}
	
	public List<PushNotificationUserDTO> getAllActiveUsers() {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PushNotificationUserDTO> list = new ArrayList<PushNotificationUserDTO>();
		try {
			pstmt = conn.prepareStatement(APNS_ACTIVE_USER_S_SQL);
			LOGGER.info(pstmt.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PushNotificationUserDTO pushNotificationUserDTO = new PushNotificationUserDTO();
				pushNotificationUserDTO.setRegId(rs.getString(1));
				pushNotificationUserDTO.setDeviceUUID(rs.getString(2));
				list.add(pushNotificationUserDTO);
			}
		} catch (Exception e) {
			LOGGER.error("Error in getAllActiveUsers :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return list;
	}

	public static void main(String[] args) {
		APNSUserDAO a = new APNSUserDAO();
		// a.registerAPNSUser("21fdga","dgkgbn");
		// a.activateAPNSUser("fdes32w2");
		a.unRegisterAPNSUser("fdes32w2");
		List<PushNotificationUserDTO> l = a.getAllActiveUsers();
		for (PushNotificationUserDTO l1 : l) {
			System.out.println(l1.getDeviceUUID()+","+l1.getRegId());
		}
	}

}
