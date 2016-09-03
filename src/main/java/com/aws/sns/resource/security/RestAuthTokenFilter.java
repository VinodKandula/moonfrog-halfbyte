/**
 * 
 */
package com.aws.sns.resource.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Vinod
 *
 */
public class RestAuthTokenFilter implements ContainerRequestFilter {
	private static final Log LOGGER = LogFactory.getLog(RestAuthTokenFilter.class);
	
	@Context
	UriInfo uriInfo; 

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		LOGGER.info("Url invoked is : " +uriInfo.getPath());
		/*String authToken = requestContext.getHeaderString("X-Auth-Token");
		
		if (!uriInfo.getPath().startsWith("token")) {
			if (StringUtil.isEmpty(authToken)) {
				requestContext.abortWith(Response
	                    .status(Response.Status.UNAUTHORIZED)
	                    //.entity("Please Request for token")
	                    .build());
				return;
			}
			
			String userUUID = TokenUtils.getUserUUIDFromToken(authToken);

			if (authToken != null && userUUID != null && TokenUtils.validateToken(authToken, userUUID)) {
					// do nothing
			} else {
				requestContext.abortWith(Response
	                    .status(Response.Status.UNAUTHORIZED)
	                    //.entity("User cannot access the resource.")
	                    .build());
			}
		}*/
		
		
	}

}
