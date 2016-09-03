/**
 * 
 */
package com.aws.sns.common.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Vinod
 *
 */
public class NotAuthorizedException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	public NotAuthorizedException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
            .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
