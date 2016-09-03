/**
 * 
 */
package com.aws.sns.dao.notifications;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.dao.DBConnector;
import com.aws.sns.dto.NotificationsHistoryDTO;

/**
 * @author Vinod
 *
 */
public class NotificationDAO {
	private static final Log LOGGER = LogFactory.getLog(NotificationDAO.class);
	
	private static final String NOTIFICATIONS_I_HISTORY = "INSERT INTO `notifications_history` (`DEVICE_UUID`, `NTID`, `NHTID`, `ACTION`) VALUES (?, ?, ?, ?)";
	
	
	public void createNotificationsHistory(NotificationsHistoryDTO notificationsHistory) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(NOTIFICATIONS_I_HISTORY);
            pstmt.setString(1, notificationsHistory.getDeviceUUID());
            pstmt.setInt(2, notificationsHistory.getNtid());
            pstmt.setInt(3, notificationsHistory.getNhtid());
            pstmt.setString(4, notificationsHistory.getAction());
            LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in createNotificationsHistory :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
    }

}
