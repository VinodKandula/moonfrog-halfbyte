/**
 * 
 */
package com.aws.sns.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author http://howtodoinjava.com/2013/07/22/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 *
 */
public class UserAuthDAO {
	private static final Log LOGGER = LogFactory.getLog(UserAuthDAO.class);
	private static String USER_PASSWORD_U_SQL = "UPDATE `user` SET `PASSWORD`=?, `SALT_KEY`=? WHERE `DEVICE_UUID`=?";
	private static String USER_PASSWORD_S_SQL = "select PASSWORD,SALT_KEY from user where PHONE =?";
	private static String USER_OTP_U_SQL = "UPDATE `user` SET NAME=?, `PHONE`=?, `OTP_STATUS`=? WHERE `DEVICE_UUID`=?";
	private static String USER_OTP_S_SQL = "select OTP_STATUS from user where DEVICE_UUID = ?";
	
	private static String getSHASecurePassword(String passwordToHash, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// Use MessageDigest md = MessageDigest.getInstance("SHA-256");
			// Use MessageDigest md = MessageDigest.getInstance("SHA-384");
			// Use MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	// Add salt
	private static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}
	
	 private static boolean validatePassword(String originalPassword, String salt, String storedPassword) {
		 String securePassword = getSHASecurePassword(originalPassword, salt); 
		 if (securePassword.equals(storedPassword)) {
			 return true;
		 }
		 return false;
	 }
	
	public String getUserOTP(String deviceUUID) {
		Connection conn = DBConnector.getPooledConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(USER_OTP_S_SQL);
			pstmt.setString(1, deviceUUID);
			LOGGER.info(pstmt.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("OTP_STATUS");
			}
		} catch (Exception e) {
			LOGGER.error("Error in createUserPassword :", e);
			throw new InternalServerErrorException();
		} finally {
			DBConnector.close(rs);
			DBConnector.close(pstmt);
			DBConnector.close(conn);
		}
		return "";
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String passwordToHash = "password";
		String salt = getSalt();
		String securePassword = getSHASecurePassword(passwordToHash, salt);
		System.out.println(securePassword +" --- " +securePassword.length());
		boolean isValidated = validatePassword(passwordToHash, salt, securePassword);
		System.out.println(isValidated);
		
		/*UserOTPDTO userOTPDTO = new UserOTPDTO();
		userOTPDTO.setDeviceUUID("ssd123");
		userOTPDTO.setPassword("password");
		userOTPDTO.setPhoneNumber(1234);
		UserAuthDAO userAuthDAO = new UserAuthDAO();
		userAuthDAO.createUserPassword(userOTPDTO);
		boolean isValid = userAuthDAO.validateUserPassword(userOTPDTO);
		System.out.println("User Password Validation : "+isValid);*/
	}

}
