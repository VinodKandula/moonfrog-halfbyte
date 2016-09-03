/**
 * 
 */
package com.aws.sns.resource.notifications;

import java.util.ArrayList;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

/**
 * @author Vinod
 *
 */
@Deprecated
public class GCMMessagePushResource {
	
	private static final String GOOGLE_SERVER_KEY = "AIzaSyD4u4Gxhbj-yH4-VUTG4PAzW8hBBlvsC0U";
	
	// project id 620918635062
	
	public void sendGCMMessage() {
		try {
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			ArrayList<String> devicesList = new ArrayList<String>();
			devicesList.add("APA91bEzjBKWCNNmL6VRImuPyukyX8ZdEpt3QK81DGf3qqG0_EgYqq7BiEC_8ACjcwdt6tZs6h5xDrwL6F9p90f0FcT3RDIUWvKdTWxlTBVs0hZG-W28MnNjBGJhHoaqd6QvEroEp7Dd");
			String data = "Hello Moonfrog!!!";
			Message message = new Message.Builder().collapseKey("moonprog")
					.timeToLive(3).delayWhileIdle(true)
					.addData("message", data).build();
			MulticastResult result = sender.send(message, devicesList, 1);
			sender.send(message, devicesList, 1);

			System.out.println(result.toString());
			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {
				}
			} else {
				int error = result.getFailure();
				System.out.println(error);
			}
		} catch (Exception e) {

		}
	}
	
	public static void main(String []args) {
		GCMMessagePushResource messagePushResource = new GCMMessagePushResource();
		messagePushResource.sendGCMMessage();
	}
	
}
