/**
 * 
 */
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

/**
 * @author Vinod
 *
 */
public class GCMUserDAO {

	private static final Log LOGGER = LogFactory.getLog(GCMUserDAO.class);

	private static String GCM_USER_I_SQL = "INSERT INTO `gcm_users` (`GCM_REGID`, `DEVICE_UUID`, `STATUS`) VALUES (?, ?, ?)";
	private static String GCM_USER_U_SQL = "UPDATE `gcm_users` SET `STATUS`=?,UPDATE_TS=CURRENT_TIMESTAMP WHERE `DEVICE_UUID`=?";
	private static String GCM_USER_R_U_SQL = "UPDATE `gcm_users` SET `STATUS`=?,UPDATE_TS=CURRENT_TIMESTAMP WHERE `DEVICE_UUID`=? and `GCM_REGID`=?";
	private static String GCM_USER_S_SQL = "select guid from gcm_users where DEVICE_UUID=? and GCM_REGID =?";
	private static String GCM_USER_REGID_S_SQL = "select GCM_REGID from gcm_users where DEVICE_UUID=? and STATUS = ?";
	private static String GCM_ACTIVE_USER_S_SQL = "select GCM_REGID,DEVICE_UUID from gcm_users where STATUS='A'";

	public long getGCMUser(String deviceUUID, String gcmRegId) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long id = -1;
		try {
			pstmt = conn.prepareStatement(GCM_USER_S_SQL);
			pstmt.setString(1, deviceUUID);
			pstmt.setString(2, gcmRegId);
			LOGGER.info(pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Exception e) {
			LOGGER.error("Error in getGCMUser :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return id;
	}

	public void registerGCMUser(String deviceUUID, String gcmRegId) {
		this.unRegisterGCMUser(deviceUUID);
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(GCM_USER_I_SQL);
			pstmt.setString(1, gcmRegId);
			pstmt.setString(2, deviceUUID);
			pstmt.setString(3, gcmRegId == null ? StatusEnum.I.toString()
					: StatusEnum.A.toString());
			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in registerGCMUser :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public void updateGCMUser(String deviceUUID, String gcmRegId) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(GCM_USER_R_U_SQL);
			pstmt.setString(1, StatusEnum.A.toString());
			pstmt.setString(2, deviceUUID);
			pstmt.setString(3, gcmRegId);

			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in updateGCMUser :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public void activateGCMUser(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(GCM_USER_U_SQL);
			pstmt.setString(1, StatusEnum.A.toString());
			pstmt.setString(2, deviceUUID);

			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in activateGCMUser :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public void unRegisterGCMUser(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(GCM_USER_U_SQL);
			pstmt.setString(1, StatusEnum.I.toString());
			pstmt.setString(2, deviceUUID);

			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in unRegisterGCMUser :", e);
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
			pstmt = conn.prepareStatement(GCM_USER_REGID_S_SQL);
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
			pstmt = conn.prepareStatement(GCM_ACTIVE_USER_S_SQL);
			LOGGER.info(pstmt.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PushNotificationUserDTO gcmUserDTO = new PushNotificationUserDTO();
				gcmUserDTO.setRegId(rs.getString(1));
				gcmUserDTO.setDeviceUUID(rs.getString(2));
				list.add(gcmUserDTO);
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

	public List<String> getAllActiveGCMUsers() {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(GCM_ACTIVE_USER_S_SQL);
			LOGGER.info(pstmt.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			LOGGER.error("Error in getAllActiveGCMUsers :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GCMUserDAO gcmUsersDAO = new GCMUserDAO();
		gcmUsersDAO.getGCMUser("test", "test");
		gcmUsersDAO.registerGCMUser("xyzz", "2wr234fwe");
		//gcmUsersDAO.unRegisterGCMUser("xyzz");

	}

}
