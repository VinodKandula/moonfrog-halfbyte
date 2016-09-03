/**
 * 
 */
package com.aws.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Vinod
 *
 */
public abstract class BaseDAO {
	private static final Log LOGGER = LogFactory.getLog(BaseDAO.class);

	public long getIdByDeviceUUID(String deviceUUID, String sql) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long id = -1;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deviceUUID);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Exception e) {
			LOGGER.error("Error in getIdByDeviceUUID :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return id;
	}
	
}
