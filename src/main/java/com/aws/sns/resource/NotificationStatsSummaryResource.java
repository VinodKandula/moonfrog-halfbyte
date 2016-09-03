/**
 * 
 */
package com.aws.sns.resource;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.dto.NotificationStatsSummary;

/**
 * @author Vinod
 *
 */
@Path("notificationStats")
public class NotificationStatsSummaryResource {
	private static final Log LOGGER = LogFactory.getLog(NotificationStatsSummaryResource.class);
	public static Random random = new Random();
	public static int aStart = 200000;
	public static int aEnd = 250000;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public NotificationStatsSummary getNotificationStats() {
		LOGGER.info("NotificationStatsSummaryResource  Start");
		NotificationStatsSummary notificationStatsSummary = new NotificationStatsSummary();
		long range = (long)aEnd - (long)aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * random.nextDouble());
	    int randomNumber =  (int)(fraction + aStart);    
		notificationStatsSummary.setNotificationCount(Math.round(randomNumber));
		
		return notificationStatsSummary;
	}

}
