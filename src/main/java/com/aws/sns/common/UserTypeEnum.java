/**
 * 
 */
package com.aws.sns.common;

/**
 * @author Vinod
 *
 */
public enum UserTypeEnum {

	USER(1),
	BUSINESS(2);

	private int userTypeId;
	UserTypeEnum(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	public int getUserTypeId() {
		return userTypeId;
	}
}
