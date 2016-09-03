/**
 * 
 */
package com.aws.sns.util;


/**
 * @author Vinod
 *
 */
public interface MoonfrogConfConstant {
	//AWS
	public static String AWS_S3_ENDPOINT = "https://s3-us-west-2.amazonaws.com/";
	public static String AWS_S3_DEV_BUCKET = "dev-bucket";
	public static String AWS_S3_PROD_BUCKET = "prod-bucket";
	public static String AWS_S3_MEDIA_BUCKET = "media";
	
	//SQL
	//Deal
	public static float NEAR_ME_RADIUS = 1.0f;
	public static int DEAL_RADIUS = 100;
	public static int MAX_DEALS = 30;
	public static int MAX_CAROUSEL_DEALS = 5;
	public static float DEAL_CHECKIN_RADIUS = 0.4f;
	 
	public static String DATE_FORMAT = "DD-MM-yyyy";
	
	public static int USER_LEADER_DASHBOARD_LIMIT = 100;
	
	public static int NOTIFICATION_CAT_MIN_DEALS = 5;
	
	public static int USER_ACTIVITY_FEED_LIMIT = 20;
	
}
