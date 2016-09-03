/**
 * 
 */
package com.aws.sns.resource;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author vinod
 *
 */
@Path("sms")
public class SMSResource {
	private static final Log LOGGER = LogFactory.getLog(SMSResource.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}/sendSMS")
	public void sendUserSMS(@PathParam("deviceUUID") String deviceUUID, String phoneNumber) {
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();
		
		//url
		String url = "http://alerts.kapsystem.com/api/web2sms.php?workingkey=<dummy>&to="
		+ phoneNumber +"&sender=Moonfrog&message=Your Message";

		// Create a method instance.
		GetMethod method = new GetMethod(url);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				LOGGER.error("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			LOGGER.info(new String(responseBody));

		} catch (HttpException e) {
			LOGGER.error("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		
	}
}
