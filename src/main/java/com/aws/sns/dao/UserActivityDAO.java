/**
 * 
 */
package com.aws.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.service.queue.message.UserActivityMessage;

/**
 * @author Vinod
 *
 */
public class UserActivityDAO {
	private static final Log LOGGER = LogFactory.getLog(UserActivityDAO.class);
	
	private static String USER_ACTIVITY_C_SQL = "INSERT INTO `user_activity` (`GAME_ID`, `GAME_NAME`, `SUB_EVENT1`, `SUB_EVENT2`, `SUB_EVENT3`, `TIMESTAMP`)  VALUES (?, ?, ?, ?, ?, ?)";
	
	public void createUserACTIVITY(UserActivityMessage userActivityDTO) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(USER_ACTIVITY_C_SQL);
			
			pstmt.setString(1, userActivityDTO.getGameId());
			pstmt.setString(2, userActivityDTO.getGameName());
			
			int i=3;
			for (String subEvent : userActivityDTO.getSubEvents()) {
				pstmt.setString(i, subEvent);
				++i;
			}
			
			pstmt.setTimestamp(6, userActivityDTO.getTimestamp());
			
			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in createUserFavBrand :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}
	
	public static void main(String[] args) {
		UserActivityDAO userActivityDAO=new UserActivityDAO();
	}

}
