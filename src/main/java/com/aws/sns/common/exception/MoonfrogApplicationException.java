/**
 * 
 */
package com.aws.sns.common.exception;

/**
 * @author Vinod
 *
 */
public class MoonfrogApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	public MoonfrogApplicationException() {
		super();
	}

	public MoonfrogApplicationException(String msg) {
		super(msg);
	}

	public MoonfrogApplicationException(String msg, Exception e) {
		super(msg, e);
	}
}
