package com.aws.sns.resource.notifications;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aws.sns.service.notifications.NotificationSchedulerManager;

@Path("notification")
public class NotificationSchedulerActivationResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("weeklyStatus")
	public boolean weeklyNotificationStatus() {
		return NotificationSchedulerManager.isWeeklyNotificationOn();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("startWeeklyActivation")
	public boolean startWeeklyNotification() {
		NotificationSchedulerManager.activateWeeklyNotifications();
		return NotificationSchedulerManager.isWeeklyNotificationOn();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("stopWeeklyActivation")
	public boolean stopWeeklyNotification() {
		NotificationSchedulerManager.stopWeeklyNotificationScheduler();
		return NotificationSchedulerManager.isWeeklyNotificationOn();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("dailyStatus")
	public boolean catNotificationStatus() {
		return NotificationSchedulerManager.isDailyNotificationOn();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("startDailyActivation")
	public boolean startCategoryNotification() {
		NotificationSchedulerManager.activateDailyNotifications();
		return NotificationSchedulerManager.isDailyNotificationOn();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("stopDailyActivation")
	public boolean stopCategoryNotification() {
		NotificationSchedulerManager.stopDailyNotificationScheduler();
		return NotificationSchedulerManager.isDailyNotificationOn();
	}
	
}
