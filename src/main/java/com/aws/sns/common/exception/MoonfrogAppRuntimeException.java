/**
 * 
 */
package com.aws.sns.common.exception;

/**
 * @author Vinod
 *
 */
public class MoonfrogAppRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MoonfrogAppRuntimeException(String message) {
		super(message);
	}

	public MoonfrogAppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
