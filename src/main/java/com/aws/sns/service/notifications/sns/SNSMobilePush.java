package com.aws.sns.service.notifications.sns;

/*
 * Copyright 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.util.IOUtils;
import com.aws.sns.service.notifications.concurrent.NotificationTaskWorkerInput;
import com.aws.sns.service.notifications.sns.SNSPlatformHelper.Platform;

public class SNSMobilePush {
	private static final Log LOGGER = LogFactory.getLog(SNSMobilePush.class);
	private AmazonSNSClientWrapper snsClientWrapper;
	public static AmazonSNS sns = null;
	public static String _APNS_CERT = null;
	public static String _APNS_PKEY = null;
	
	static {
		try {
			sns = new AmazonSNSClient(new PropertiesCredentials(
					SNSMobilePush.class
							.getResourceAsStream("AwsCredentials.properties")));
			
			//TODO generate APNS Certificate & Key
			
			/*InputStream inputStream = SNSMobilePush.class.getResourceAsStream("apns_cert.pem");
			_APNS_CERT = IOUtils.toString(inputStream);
			IOUtils.closeQuietly(inputStream, null);
			
			inputStream = SNSMobilePush.class.getResourceAsStream("apns_pkey.pem");
			_APNS_PKEY = IOUtils.toString(inputStream);
			IOUtils.closeQuietly(inputStream, null);*/
			
		} catch (IOException e) {
			LOGGER.error("Error creating AmazonSNS client : "+sns);
		}
		sns.setEndpoint("https://sns.us-west-2.amazonaws.com");
	}
	
	public SNSMobilePush(AmazonSNS snsClient) {
		this.snsClientWrapper = new AmazonSNSClientWrapper(snsClient);
	}

	public static final Map<Platform, Map<String, MessageAttributeValue>> attributesMap = new HashMap<Platform, Map<String, MessageAttributeValue>>();
	static {
		attributesMap.put(Platform.ADM, null);
		attributesMap.put(Platform.GCM, null);
		attributesMap.put(Platform.APNS, null);
		attributesMap.put(Platform.APNS_SANDBOX, null);
		attributesMap.put(Platform.BAIDU, addBaiduNotificationAttributes());
		attributesMap.put(Platform.WNS, addWNSNotificationAttributes());
		attributesMap.put(Platform.MPNS, addMPNSNotificationAttributes());
	}
	
	public static void sendPushNotifications(NotificationTaskWorkerInput input) {
		try {
			
			System.out.println("===========================================\n");
			System.out.println("Getting Started with Amazon SNS");
			System.out.println("===========================================\n");
			try {
				SNSMobilePush sample = new SNSMobilePush(sns);
				if (SNSPlatformHelper.Platform.GCM.name() == input.getPlatform().name()) {
					sample.demoAndroidAppNotification(input.getToken(), input.getMessage(),input.getAction(), input.getCollapseKey());
				} else if (SNSPlatformHelper.Platform.APNS.name() == input.getPlatform().name()) {
					sample.demoAppleAppNotification(input.getToken(), input.getMessage(),input.getAction(), input.getCollapseKey());
				} else {
					LOGGER.error("Unsupported SNS Notification Service :"+input.getPlatform().name());
				}
				
				// sample.demoKindleAppNotification();
				// sample.demoAppleAppNotification();
				// sample.demoAppleSandboxAppNotification();
				// sample.demoBaiduAppNotification();
				// sample.demoWNSAppNotification();
				// sample.demoMPNSAppNotification();
			} catch (AmazonServiceException ase) {
				System.out
						.println("Caught an AmazonServiceException, which means your request made it "
								+ "to Amazon SNS, but was rejected with an error response for some reason.");
				System.out.println("Error Message:    " + ase.getMessage());
				System.out.println("HTTP Status Code: " + ase.getStatusCode());
				System.out.println("AWS Error Code:   " + ase.getErrorCode());
				System.out.println("Error Type:       " + ase.getErrorType());
				System.out.println("Request ID:       " + ase.getRequestId());
			} catch (AmazonClientException ace) {
				System.out
						.println("Caught an AmazonClientException, which means the client encountered "
								+ "a serious internal problem while trying to communicate with SNS, such as not "
								+ "being able to access the network.");
				System.out.println("Error Message: " + ace.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error("Error in sendPushNofifications :", e);
		}
		
	}

	public void demoAndroidAppNotification(String regId, String messageText, String payloadAction, String collapseKey) {
		// TODO: Please fill in following values for your application. You can
		// also change the notification payload as per your preferences using
		// the method
		// com.amazonaws.sns.samples.tools.SampleMessageGenerator.getSampleAndroidMessage()
		//String serverAPIKey = "AIzaSyAdoOVztHBvp0mtH8dyviwbqEshffZrCxA";
		//String applicationName = "elite-partition-795";
		//emulator 
	    //String registrationId = "APA91bEimvJLtmXuNAxv-YDuKVAMbkRpjpKevLs1YRhZgY6ZirniQUHuGWefvGP9xmSNlYcCDVLvHbN5Kf8lBiZeDqy7i6jHJV4v0U9aKgU3RfUiwmkSKbYNwzj7QEBA7mNPlTwrNpYRQRT8XgFvnFg9dQUIgO5Q4w";
		snsClientWrapper.demoNotification(Platform.GCM, "", SNSConstants.GCM_SERVER_APIKEY,
				regId, SNSConstants.GCM_APPLICATION_NAME, attributesMap, messageText, payloadAction, collapseKey);
	}

	public void demoAppleAppNotification(String deviceToken, String messageText, String payloadAction) {
		demoAppleAppNotification(deviceToken, messageText, payloadAction, null);
	}

	public void demoAppleAppNotification(String deviceToken, String messageText, String payloadAction, String collapseKey) {
		// TODO: Please fill in following values for your application. You can
		// also change the notification payload as per your preferences using
		// the method
		// com.amazonaws.sns.samples.tools.SampleMessageGenerator.getSampleAppleMessage()
		//String certificate = ""; // This should be in pem format with \n at the
									// end of each line.
		//String privateKey = ""; // This should be in pem format with \n at the
								// end of each line.
		//String applicationName = "";
		//String deviceToken = ""; // This is 64 hex characters.
		snsClientWrapper.demoNotification(Platform.APNS_SANDBOX, _APNS_CERT,
				_APNS_PKEY, deviceToken, SNSConstants.APNS_APPLICATION_NAME, attributesMap, messageText, payloadAction, collapseKey);
	}


	private static Map<String, MessageAttributeValue> addBaiduNotificationAttributes() {
		Map<String, MessageAttributeValue> notificationAttributes = new HashMap<String, MessageAttributeValue>();
		notificationAttributes.put("AWS.SNS.MOBILE.BAIDU.DeployStatus",
				new MessageAttributeValue().withDataType("String")
						.withStringValue("1"));
		notificationAttributes.put("AWS.SNS.MOBILE.BAIDU.MessageKey",
				new MessageAttributeValue().withDataType("String")
						.withStringValue("default-channel-msg-key"));
		notificationAttributes.put("AWS.SNS.MOBILE.BAIDU.MessageType",
				new MessageAttributeValue().withDataType("String")
						.withStringValue("0"));
		return notificationAttributes;
	}

	private static Map<String, MessageAttributeValue> addWNSNotificationAttributes() {
		Map<String, MessageAttributeValue> notificationAttributes = new HashMap<String, MessageAttributeValue>();
		notificationAttributes.put("AWS.SNS.MOBILE.WNS.CachePolicy",
				new MessageAttributeValue().withDataType("String")
						.withStringValue("cache"));
		notificationAttributes.put("AWS.SNS.MOBILE.WNS.Type",
				new MessageAttributeValue().withDataType("String")
						.withStringValue("wns/badge"));
		return notificationAttributes;
	}

	private static Map<String, MessageAttributeValue> addMPNSNotificationAttributes() {
		Map<String, MessageAttributeValue> notificationAttributes = new HashMap<String, MessageAttributeValue>();
		notificationAttributes.put("AWS.SNS.MOBILE.MPNS.Type",
				new MessageAttributeValue().withDataType("String")
						.withStringValue("token")); // This attribute is required.
		notificationAttributes.put("AWS.SNS.MOBILE.MPNS.NotificationClass",
				new MessageAttributeValue().withDataType("String")
						.withStringValue("realtime")); // This attribute is required.
														
		return notificationAttributes;
	}
	
	public static void main(String[] args) throws IOException {
		/*
		 * TODO: Be sure to fill in your AWS access credentials in the
		 * AwsCredentials.properties file before you try to run this sample.
		 * http://aws.amazon.com/security-credentials
		 */
		AmazonSNS sns = new AmazonSNSClient(new PropertiesCredentials(
				SNSMobilePush.class
						.getResourceAsStream("AwsCredentials.properties")));

		sns.setEndpoint("https://sns.us-west-2.amazonaws.com");
		System.out.println("===========================================\n");
		System.out.println("Getting Started with Amazon SNS");
		System.out.println("===========================================\n");
		try {
			SNSMobilePush sample = new SNSMobilePush(sns);
			/* TODO: Uncomment the services you wish to use. */
			   String registrationId = "APA91bEzO4gs7gqC0PPaw1RKzlDY5cEmRwGzV4k5DPzc_uxp8aLyNVGS3Wx7G6O3lj9v17aqUBtoTyg3JZvbsOVt81mdUDTDDiXoiWLJt9PtcWUUNKYyJsiaq1OlPnSNRx2djcfPS7Pp";
			   sample.demoAndroidAppNotification(registrationId, "Test Message", "payload action", "Moonfrog");
			// sample.demoKindleAppNotification();
			//   sample.demoAppleAppNotification("c522823644bf4e799d94adc7bc4c1f1dd57066a38cc72b7d37cf48129379c13b", "APNS Welcome !!!", "apns", "Moonfrog");
			// sample.demoAppleSandboxAppNotification();
			// sample.demoBaiduAppNotification();
			// sample.demoWNSAppNotification();
			// sample.demoMPNSAppNotification();
		} catch (AmazonServiceException ase) {
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon SNS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with SNS, such as not "
							+ "being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}
	
}
