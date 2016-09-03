package com.aws.sns.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.dto.AWSAccessAuthDTO;

/**
 * @author Vinod
 *
 */

public class AWSAccessAuthProvider {
	
	private static final Log LOGGER = LogFactory.getLog(AWSAccessAuthProvider.class);
	
	private static String policy_document =
					      "{\"expiration\": \"2017-01-01T00:00:00Z\"," +
					        "\"conditions\": [" +
					          "{\"bucket\": \"moonfrog-dev-bucket\"}," +
					          "[\"starts-with\", \"$key\", \"\"]," +
					          "{\"acl\": \"public-read\"}," +
					          "[\"starts-with\", \"$Content-Type\", \"\"]," +
					          "[\"content-length-range\", 0, 2048576]" +
					        "]" +
					      "}";

	private static String accessKey = "AKIAIWKPHOYAQJHJ7U6A";
	private static String secretKey = "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";
	
	private static String policy = null;
	private static String signature = null;
	
	
	private AWSAccessAuthProvider () {
	}
	
	private static synchronized AWSAccessAuthDTO sign() {
		AWSAccessAuthDTO aesAccessAuthDTO = new AWSAccessAuthDTO();
		try {
				// Calculate policy and signature values from the given policy document and AWS credentials.
			    policy = new String(Base64.encodeBase64(policy_document.getBytes("UTF-8")), "ASCII");

			    Mac hmac = Mac.getInstance("HmacSHA1");
			    hmac.init(new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA1"));
			    signature = new String(Base64.encodeBase64(hmac.doFinal(policy.getBytes("UTF-8"))), "ASCII");
			    aesAccessAuthDTO = new AWSAccessAuthDTO();
			    aesAccessAuthDTO.setKey(accessKey);
			    aesAccessAuthDTO.setPolicy(policy);
			    aesAccessAuthDTO.setSignature(signature);
			    
		} catch (Exception e) {
			LOGGER.error("Error in S3AccessProvider :", e);
			throw new InternalServerErrorException();
		}
	    return aesAccessAuthDTO;
	}
	
	public static AWSAccessAuthDTO getAWSAccessAuthDetails() {
		return sign();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}