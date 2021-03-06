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
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class SNSPlatformHelper {

	/*
	 * This message is delivered if a platform specific message is not specified
	 * for the end point. It must be set. It is received by the device as the
	 * value of the key "default".
	 */
	public static final String defaultMessage = "This is the default message";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static enum Platform {
		// Apple Push Notification Service
		APNS,
		// Sandbox version of Apple Push Notification Service
		APNS_SANDBOX,
		// Amazon Device Messaging
		ADM,
		// Google Cloud Messaging
		GCM,
		// Baidu CloudMessaging Service
		BAIDU,
		// Windows Notification Service
		WNS,
		// Microsoft Push Notificaion Service
		MPNS;
	}

	public static String jsonify(Object message) {
		try {
			return objectMapper.writeValueAsString(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw (RuntimeException) e;
		}
	}

	private static Map<String, String> getData() {
		Map<String, String> payload = new HashMap<String, String>();
		payload.put("message", "Welcome to Madszz Notificationssssss!!!");
		return payload;
	}
	
	private static Map<String, String> getData(String messageText, String payloadAction) {
		Map<String, String> payload = new HashMap<String, String>();
		payload.put("message", messageText);
		payload.put("title", "Moonfrog HalfByte!!!");
		//payload.put("msgcnt", "1");
		payload.put("action", payloadAction); // change the state as per the notification type
		return payload;
	}

	public static String getSampleAppleMessage(String message, String payloadAction, String collapseKey) {
		Map<String, Object> appleMessageMap = new HashMap<String, Object>();
		Map<String, Object> appMessageMap = new HashMap<String, Object>();
		appMessageMap.put("alert", message);
		appMessageMap.put("action", payloadAction);
		appMessageMap.put("badge", 9);
		appMessageMap.put("sound", "default");
		appleMessageMap.put("aps", appMessageMap);
		return jsonify(appleMessageMap);
	}

	public static String getSampleKindleMessage() {
		Map<String, Object> kindleMessageMap = new HashMap<String, Object>();
		kindleMessageMap.put("data", getData());
		kindleMessageMap.put("consolidationKey", "Welcome");
		kindleMessageMap.put("expiresAfter", 1000);
		return jsonify(kindleMessageMap);
	}

	public static String getSampleAndroidMessage(String message, String payloadAction, String collapseKey) {
		Map<String, Object> androidMessageMap = new HashMap<String, Object>();
		androidMessageMap.put("collapse_key", collapseKey == null ? "moonfrog" : collapseKey);
		androidMessageMap.put("data", getData(message, payloadAction));
		androidMessageMap.put("delay_while_idle", true); // Wait for device to become active before sending.
		androidMessageMap.put("time_to_live", 600);
		androidMessageMap.put("dry_run", false);
		return jsonify(androidMessageMap);
	}

	public static String getSampleBaiduMessage() {
		Map<String, Object> baiduMessageMap = new HashMap<String, Object>();
		baiduMessageMap.put("title", "New Notification Received from SNS");
		baiduMessageMap.put("description", "Hello World!");
		return jsonify(baiduMessageMap);
	}

	public static String getSampleWNSMessage() {
		Map<String, Object> wnsMessageMap = new HashMap<String, Object>();
		wnsMessageMap.put("version", "1");
		wnsMessageMap.put("value", "23");
		return "<badge version=\"" + wnsMessageMap.get("version")
				+ "\" value=\"" + wnsMessageMap.get("value") + "\"/>";
	}

	public static String getSampleMPNSMessage() {
		Map<String, String> mpnsMessageMap = new HashMap<String, String>();
		mpnsMessageMap.put("count", "23");
		mpnsMessageMap.put("payload", "This is a tile notification");
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><wp:Notification xmlns:wp=\"WPNotification\"><wp:Tile><wp:Count>"
				+ mpnsMessageMap.get("count")
				+ "</wp:Count><wp:Title>"
				+ mpnsMessageMap.get("payload")
				+ "</wp:Title></wp:Tile></wp:Notification>";
	}
}