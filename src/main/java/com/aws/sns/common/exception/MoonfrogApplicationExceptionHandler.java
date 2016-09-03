/**
 * 
 */
package com.aws.sns.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Vinod
 *
 */
@Provider
public class MoonfrogApplicationExceptionHandler implements ExceptionMapper<MoonfrogApplicationException> {
    @Override
    public Response toResponse(MoonfrogApplicationException exception) {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build(); 
    }
}
