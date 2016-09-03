/**
 * 
 */
package com.aws.sns.resource.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Vinod
 *
 */
public class TokenUtils {
	private static final Log LOGGER = LogFactory.getLog(TokenUtils.class);

	public static final String MAGIC_KEY = "Mad77Ma5h10825";

	public static String createToken(String userUUID) {
		/* Expires in one hour */
		//long expires = System.currentTimeMillis() + 1000L * 60 * 60;
		long expires = System.currentTimeMillis() + 1000L * 10;

		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(userUUID);
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(TokenUtils.computeSignature(userUUID, expires));
		LOGGER.info("Token created : "+tokenBuilder.toString());
		return tokenBuilder.toString();
	}

	public static String computeSignature(String userUUID, long expires) {
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(userUUID);
		signatureBuilder.append(":");
		signatureBuilder.append(expires);
		signatureBuilder.append(":");
		signatureBuilder.append(TokenUtils.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encodeHexString(digest.digest(signatureBuilder
				.toString().getBytes())));
	}

	public static String getUserUUIDFromToken(String authToken) {
		if (null == authToken) {
			return null;
		}

		String[] parts = authToken.split(":");
		return parts[0];
	}

	public static boolean validateToken(String authToken, String userUUID) {
		String[] parts = authToken.split(":");
		if (parts.length < 3) {
			return false;
		}
		long expires = Long.parseLong(parts[1]);
		String signature = parts[2];

		if (expires < System.currentTimeMillis()) {
			return false;
		}

		return signature.equals(TokenUtils.computeSignature(userUUID, expires));
	}
}
