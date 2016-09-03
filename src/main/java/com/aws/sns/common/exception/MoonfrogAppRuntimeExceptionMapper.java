/**
 * 
 */
package com.aws.sns.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Vinod
 *
 */
@Provider
public class MoonfrogAppRuntimeExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;

		// if (exception instanceof BusinessException)
		// httpStatus = Response.Status.BAD_REQUEST;

		return Response.status(httpStatus).entity(exception.getMessage()).build();
	}

}