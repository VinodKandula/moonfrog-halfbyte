/**
 * 
 */
package com.aws.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.dto.UserDTO;
import com.aws.sns.dto.UserDemoProfileDTO;


/**
 * @author Vinod
 *
 */
public class UserDAO extends BaseDAO {
	private static final Log LOGGER = LogFactory.getLog(UserDAO.class);
	
	private static String USERS_SQL = "SELECT uid, name FROM user LIMIT 0 , 100";
	private static String USER_SQL = "SELECT uid,name,phone FROM user where DEVICE_UUID = ? AND OTP_STATUS = 'A'";
	private static String USER_CAT_SQL = "select cid from user_category where DEVICE_UUID = ? ";
	private static String USER_UID_S_SQL = "SELECT UID FROM user where DEVICE_UUID = ?";
	private static String USER_DEVICE_UUID_C_SQL = "INSERT INTO user (`DEVICE_UUID`) VALUES (?)";
	private static String USER_CAT_C_SQL = "INSERT INTO user_category (`DEVICE_UUID`, `CID`) VALUES (?, ?)";
	@SuppressWarnings("unused")
	private static String USER_CAT_U_SQL = "UPDATE user_category set CID = ? WHERE  DEVICE_UUID = ? ";
	private static String USER_CAT_D_SQL = "DELETE from user_category WHERE  DEVICE_UUID = ? ";
	private static String USER_DEMO_PROFILE_S_SQL = "select ud.UDID,ud.UID,ud.NAME,ud.FIRST_NAME,ud.LAST_NAME,ud.GENDER,ud.AGE,ud.BIRTH_DATE,ud.MARITAL_STATUS,ud.EMAIL,ud.LOCATION,ud.LOGIN_TYPE,ud.LOGIN_ID,ud.PROFILE_IMAGE_URL from user u join user_demographic ud on u.UID = ud.UID where u.DEVICE_UUID = ? order by ud.update_ts desc limit 1";
	private static String USER_DEMO_PROFILE_S_SQL_1 = "select ud.UDID,ud.UID,ud.NAME,ud.FIRST_NAME,ud.LAST_NAME,ud.GENDER,ud.AGE,ud.BIRTH_DATE,ud.MARITAL_STATUS,ud.EMAIL,ud.LOCATION,ud.LOGIN_TYPE,ud.LOGIN_ID,ud.PROFILE_IMAGE_URL from user u join user_demographic ud on u.UID = ud.UID where u.DEVICE_UUID = ? and LOGIN_ID = ? order by ud.update_ts desc limit 1";
	private static String USER_DEMO_C_SQL = "INSERT INTO user_demographic (`UID`, `NAME`, `FIRST_NAME`, `LAST_NAME`, `GENDER`, `AGE`, `BIRTH_DATE`, `LOCATION`, `LOGIN_TYPE`, `LOGIN_ID`, `EMAIL`, `MARITAL_STATUS`, `PROFILE_IMAGE_URL`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static String USER_DEMO_U_SQL = "UPDATE `user_demographic` SET `UID`=?, `NAME`=?, `FIRST_NAME`=?, `LAST_NAME`=?, `GENDER`=?, `AGE`=?, `BIRTH_DATE`=?, `LOCATION`=?, `LOGIN_TYPE`=?, `LOGIN_ID`=?, `EMAIL`=?, `MARITAL_STATUS`=?, `PROFILE_IMAGE_URL`=?, `UPDATE_TS` = current_timestamp WHERE `UDID`=?";
	/*private static String USER_BRAND_S_SQL = "select distinct b.BID,b.BNAME,b.LOGO_URL from brand b join cat_subcat_brand csb on b.BID = csb.BID join sub_category sc on sc.SCID = csb.SCID join category c on c.CID = sc.CID "
			+ " join (select CID, (case when cid = 11 or cid = 15 then 1"
            + " else 0 end) as catFlag from user_category where DEVICE_UUID = ? )uc "
            + " on c.CID = uc.CID and b.FLAG = catFlag"
			+ " and b.BID not in (select BID from user_fav_brand where DEVICE_UUID = ?) order by bname limit ?,?";
	*/
	private static String USER_BRAND_S_SQL = "select distinct b.BID,b.BNAME,b.LOGO_URL from brand b "
			+ " where b.FLAG = 1 and b.BID not in (select BID from user_fav_brand where DEVICE_UUID = ?) order by bname limit ?,?";
	private static String USER_SEARCH_HISTORY = "INSERT INTO user_search_hist(DEVICE_UUID,SEARCH_TOKEN,LAT,LNG,START,END,RESULT_CNT) VALUES (?,?,?,?,?,?,?)";
	
	public List<UserDTO> getUsers() {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserDTO> list = new ArrayList<UserDTO>();
		try { 
			pstmt = conn.prepareStatement(USERS_SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				UserDTO dto = new UserDTO();
				dto.setUid(rs.getInt("uid"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (Exception e) {
			LOGGER.error("Error in getUsers :", e);
			throw new InternalServerErrorException();
		} finally {
			// finally block used to close resources
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}// end try
		return list;
	}
	
	public UserDemoProfileDTO getUserByOTP(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDemoProfileDTO userDto = new UserDemoProfileDTO();
		try { 
			pstmt = conn.prepareStatement(USER_SQL);
			pstmt.setString(1, deviceUUID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDto.setUid(rs.getInt("uid"));
				userDto.setName(rs.getString("name"));
				userDto.setPhone(rs.getString("phone"));
			}
		} catch (Exception e) {
			LOGGER.error("Error in getUserByOTP :", e);
			throw new InternalServerErrorException();
		} finally {
			// finally block used to close resources
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}// end try
		return userDto;
	}

	public List<Integer> getUserCategories(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			pstmt = conn.prepareStatement(USER_CAT_SQL);
			pstmt.setString(1, deviceUUID);
			LOGGER.info(pstmt.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("cid"));
			}
		} catch (Exception e) {
			LOGGER.error("Error in getUserCategories :", e);
			throw new InternalServerErrorException();
		} finally {
			// finally block used to close resources
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}// end try
		return list;
	}
	
	public long getUserIdByDeviceUUID(String deviceUUID) {
		return getIdByDeviceUUID(deviceUUID, USER_UID_S_SQL);
	}
	
	public void createUserByDeviceUUID(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(USER_DEVICE_UUID_C_SQL);
			pstmt.setString(1, deviceUUID);
			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in createUserByDeviceUUID :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}

	public UserDemoProfileDTO getUserDemoProfiles(String deviceUUID, String loginId) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDemoProfileDTO userDemoProfileDTO = null;
		try {
			if (loginId == null) {
				pstmt = conn.prepareStatement(USER_DEMO_PROFILE_S_SQL);
				pstmt.setString(1, deviceUUID);
			} else {
				pstmt = conn.prepareStatement(USER_DEMO_PROFILE_S_SQL_1);
				pstmt.setString(1, deviceUUID);
				pstmt.setString(2, loginId);
			}
			LOGGER.info(pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDemoProfileDTO = new UserDemoProfileDTO();
				userDemoProfileDTO.setUdid(rs.getInt("udid"));
				userDemoProfileDTO.setUid(rs.getInt("uid"));
				userDemoProfileDTO.setName(rs.getString("name"));
				userDemoProfileDTO.setFirstName(rs.getString("FIRST_NAME"));
				userDemoProfileDTO.setLastName(rs.getString("LAST_NAME"));
				userDemoProfileDTO.setGender(rs.getString("GENDER"));
				userDemoProfileDTO.setAge(rs.getInt("AGE"));
				userDemoProfileDTO.setBirthDate(rs.getString("BIRTH_DATE"));
				userDemoProfileDTO.setMaritalStatus(rs.getString("MARITAL_STATUS"));
				userDemoProfileDTO.setEmail(rs.getString("EMAIL"));
				userDemoProfileDTO.setLocation(rs.getString("LOCATION"));
				userDemoProfileDTO.setLoginType(rs.getString("LOGIN_TYPE"));
				userDemoProfileDTO.setLoginId(rs.getString("LOGIN_ID"));
				userDemoProfileDTO.setProfileImageUrl(rs.getString("PROFILE_IMAGE_URL"));
			}
		} catch (Exception e) {
			LOGGER.error("Error in getUserDemoProfiles :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return userDemoProfileDTO;
	}
	
	public UserDemoProfileDTO getUserDemoProfiles(String deviceUUID) {
		return getUserDemoProfiles(deviceUUID, null);
	}
	
	public void createUserDemoProfile(UserDemoProfileDTO userDemoProfileDTO) {
		LOGGER.info("User Demografic : "+userDemoProfileDTO.toString());
		long uid = getUserIdByDeviceUUID(userDemoProfileDTO.getDeviceUUID());
		if (uid == -1) {
			LOGGER.warn("createUserProfile : User Id can't be null");
			return;
		} 
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(USER_DEMO_C_SQL);
			pstmt.setLong(1, uid);
			pstmt.setString(2, userDemoProfileDTO.getName());
			pstmt.setString(3, userDemoProfileDTO.getFirstName());
			pstmt.setString(4, userDemoProfileDTO.getLastName());
			pstmt.setString(5, userDemoProfileDTO.getGender());
			pstmt.setInt(6, userDemoProfileDTO.getAge());
			pstmt.setString(7, userDemoProfileDTO.getBirthDate());
			pstmt.setString(8, userDemoProfileDTO.getLocation());
			pstmt.setString(9, userDemoProfileDTO.getLoginType());
			pstmt.setString(10, userDemoProfileDTO.getLoginId());
			pstmt.setString(11, userDemoProfileDTO.getEmail());
			pstmt.setString(12, userDemoProfileDTO.getMaritalStatus());
			pstmt.setString(13, userDemoProfileDTO.getProfileImageUrl());
			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in getUserDemoProfiles :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}
	
	public void updateUserDemoProfile(UserDemoProfileDTO userDemoProfileDTO) {
		LOGGER.info("User Demografic Update : "+userDemoProfileDTO.toString());
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(USER_DEMO_U_SQL);
			pstmt.setLong(1, userDemoProfileDTO.getUid());
			pstmt.setString(2, userDemoProfileDTO.getName());
			pstmt.setString(3, userDemoProfileDTO.getFirstName());
			pstmt.setString(4, userDemoProfileDTO.getLastName());
			pstmt.setString(5, userDemoProfileDTO.getGender());
			pstmt.setInt(6, userDemoProfileDTO.getAge());
			pstmt.setString(7, userDemoProfileDTO.getBirthDate());
			pstmt.setString(8, userDemoProfileDTO.getLocation());
			pstmt.setString(9, userDemoProfileDTO.getLoginType());
			pstmt.setString(10, userDemoProfileDTO.getLoginId());
			pstmt.setString(11, userDemoProfileDTO.getEmail());
			pstmt.setString(12, userDemoProfileDTO.getMaritalStatus());
			pstmt.setString(13, userDemoProfileDTO.getProfileImageUrl());
			pstmt.setLong(14, userDemoProfileDTO.getUdid());
			LOGGER.info(pstmt.toString());
			pstmt.execute();
		} catch (Exception e) {
			LOGGER.error("Error in updateUserDemoProfile :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
	}		

}
