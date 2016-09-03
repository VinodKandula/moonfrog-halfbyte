/**
 * 
 */
package com.aws.sns.resource.security;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.aws.sns.util.StringUtil;

/**
 * @author Vinod
 *
 */
@Path("authToken")
public class AuthTokenRequestResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{deviceUUID}")
	public TokenTransfer requestAuthorizedToken(@PathParam("deviceUUID") String userUUID) {
		if (StringUtil.isEmpty(userUUID)) {
			//userUUID = "dummyUUID";
			throw new WebApplicationException(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return new TokenTransfer(TokenUtils.createToken(userUUID));
	}
}
