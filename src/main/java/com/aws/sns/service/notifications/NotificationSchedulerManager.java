/**
 * 
 */
package com.aws.sns.service.notifications;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.service.notifications.concurrent.NotificationExecutorService;

/**
 * @author Vinod
 *
 */
public class NotificationSchedulerManager {
	private static final Log LOGGER = LogFactory.getLog(NotificationSchedulerManager.class);
	
	private static ScheduledExecutorService wScheduler;
	private static ScheduledExecutorService dScheduler;
	private static ScheduledExecutorService bScheduler;
	private static ScheduledFuture<?> wFuture;
	private static ScheduledFuture<?> dFuture;
	private static ScheduledFuture<?> bFuture;
	@SuppressWarnings("unused")
	private final static long wInitialDelay = 0;
	private final static long wDelayBetweenRuns = 7*24*60;
	private final static long wShutdownAfter = 20;
	@SuppressWarnings("unused")
	private final static long dInitialDelay = 5;
	private final static long dDelayBetweenRuns = 24*60;
	private final static long dShutdownAfter = 20;
	
	private NotificationSchedulerManager() {
	}

	private static void log(String aMsg) {
		LOGGER.info(aMsg);
	}

	/** If invocations might overlap, you can specify more than a single thread. */
	//private static final int NUM_THREADS = 1;
	private static final boolean DONT_INTERRUPT_IF_RUNNING = true;
	
	public static boolean isWeeklyNotificationOn() {
		if (wScheduler  == null)
			return false;
		return !wScheduler.isTerminated();
	}
	
	public static boolean isDailyNotificationOn() {
		if (dScheduler  == null)
			return false;
		return !dScheduler.isTerminated();
	}
	
	public static void activateWeeklyNotifications() {
		TimeZone tz = TimeZone.getTimeZone("IST");
		Calendar calTZ = new GregorianCalendar(tz);
		calTZ.setTimeInMillis(new Date().getTime());
		
		Calendar today = Calendar.getInstance();
		today.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
		today.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
		today.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
		today.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
		today.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
		today.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
		
		System.out.println("Today : "+today.getTime());
		int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
		int daysUntilNextFriday = Calendar.FRIDAY - dayOfWeek;
		if(daysUntilNextFriday < 0){
		    daysUntilNextFriday = daysUntilNextFriday + 7;
		} else if (daysUntilNextFriday == 0) {
			if (today.get(Calendar.HOUR_OF_DAY) > 16 ) {
				daysUntilNextFriday = daysUntilNextFriday + 7;
			}
		}
		Calendar nextFriday = (Calendar)today.clone();
		nextFriday.add(Calendar.DAY_OF_WEEK, daysUntilNextFriday);
		nextFriday.set(Calendar.HOUR_OF_DAY, 16);
		nextFriday.set(Calendar.MINUTE, 00);
		nextFriday.set(Calendar.SECOND, 00);
		System.out.println("Next Friday : "+nextFriday.getTime());
		long delayInMinutes = nextFriday.getTimeInMillis()/(1000*60) - today.getTimeInMillis()/(1000*60);
		LOGGER.info("###### Next Friday delay in munites: "+delayInMinutes);
		
		LOGGER.info("################ Weekly Notifications Start @ "+nextFriday.getTime());
		wScheduler = Executors.newSingleThreadScheduledExecutor();
		Runnable weeklyNotification = new WeeklyNotificationScheduler();
		wFuture = wScheduler.scheduleWithFixedDelay(weeklyNotification, delayInMinutes,
						wDelayBetweenRuns, TimeUnit.MINUTES);
		if (wFuture.isDone()) {
			LOGGER.info("################ Weekly Notifications End ###################### ");
		}
	}
	
	public static void stopWeeklyNotificationScheduler() {
		Runnable stopAlarm = new StopWeeklyNotificationScheduler(wFuture);
		wScheduler.schedule(stopAlarm, wShutdownAfter, TimeUnit.SECONDS);
	}
	
	public static void activateDailyNotifications() {
		
		Calendar calTZ = new GregorianCalendar(TimeZone.getTimeZone("IST"));
		calTZ.setTimeInMillis(new Date().getTime());
        Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
		int minutesPassed12AM = hour * 60 + minutes;
		@SuppressWarnings("unused")
		int minutesAt3AM = 3 * 60;
		int minutesAt3PM = 16 * 60;
		int oneDayMinutes = 24 * 60;
		long delayInMinutes = minutesPassed12AM <= minutesAt3PM ? minutesAt3PM - minutesPassed12AM : oneDayMinutes - (minutesPassed12AM - minutesAt3PM);
	 
		LOGGER.info("################ Daily Category Notifications Start After "+delayInMinutes +" minutes");
		dScheduler = Executors.newSingleThreadScheduledExecutor();
		Runnable dailyNotification = new DailyNotificationScheduler();
		dFuture = dScheduler.scheduleWithFixedDelay(dailyNotification, delayInMinutes, dDelayBetweenRuns, TimeUnit.MINUTES);
		
		if (dFuture.isDone()) {
			LOGGER.info("################ Daily Category Notifications End ###################### ");
		}
	}
	
	public static void stopDailyNotificationScheduler() {
		Runnable stopAlarm = new StopDailyNotificationScheduler(dFuture);
		dScheduler.schedule(stopAlarm, dShutdownAfter, TimeUnit.SECONDS);
	}
		
	private static final class WeeklyNotificationScheduler implements Runnable {
		@Override
		public void run() {
			++fCount;
			log("WeeklyNotificationScheduler beep " + fCount);
			NotificationExecutorService notificationScheduler = new NotificationExecutorService(); 
			notificationScheduler.performWeeklyBulkNotification();
		}

		private int fCount;
	}
	
	private static final class DailyNotificationScheduler implements Runnable {
		@Override
		public void run() {
			Calendar indiaTime = new GregorianCalendar(TimeZone.getTimeZone("IST"));
	        indiaTime.setTimeInMillis(indiaTime.getTimeInMillis());
	        int day = indiaTime.get(Calendar.DAY_OF_WEEK);
	        if (day == 6) {
	        	LOGGER.info("Daily Category Notification skip on Fridays : "+indiaTime.getTime());
	        	return;
	        }
			++fCount;
			log("DailyCategoryNotificationScheduler beep " + fCount);
			NotificationExecutorService notificationScheduler = new NotificationExecutorService(); 
			notificationScheduler.performDailyNotification();
		}

		private int fCount;
	}
	

	private static final class StopWeeklyNotificationScheduler implements Runnable {
		StopWeeklyNotificationScheduler(ScheduledFuture<?> aSchedFuture) {
			fSchedFuture = aSchedFuture;
		}

		@Override
		public void run() {
			log("Stopping Weekly Notification Scheduler.");
			fSchedFuture.cancel(DONT_INTERRUPT_IF_RUNNING);
			/*
			 * Note that this Task also performs cleanup, by asking the
			 * scheduler to shutdown gracefully.
			 */
			wScheduler.shutdown();
		}
		private ScheduledFuture<?> fSchedFuture;
	}
	
	private static final class StopDailyNotificationScheduler implements Runnable {
		StopDailyNotificationScheduler(ScheduledFuture<?> aSchedFuture) {
			fSchedFuture = aSchedFuture;
		}

		@Override
		public void run() {
			log("Stopping Daily Category Notification Scheduler.");
			fSchedFuture.cancel(DONT_INTERRUPT_IF_RUNNING);
			/*
			 * Note that this Task also performs cleanup, by asking the
			 * scheduler to shutdown gracefully.
			 */
			dScheduler.shutdown();
		}
		private ScheduledFuture<?> fSchedFuture;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NotificationSchedulerManager.activateWeeklyNotifications();
		NotificationSchedulerManager.activateDailyNotifications();
		NotificationSchedulerManager.stopWeeklyNotificationScheduler();
		NotificationSchedulerManager.stopDailyNotificationScheduler();
	}

}
