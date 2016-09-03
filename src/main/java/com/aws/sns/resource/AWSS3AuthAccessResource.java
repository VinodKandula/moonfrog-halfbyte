/**
 * 
 */
package com.aws.sns.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aws.sns.dto.AWSAccessAuthDTO;
import com.aws.sns.util.AWSAccessAuthProvider;

/**
 * @author Vinod
 *
 */
@Path("awsS3AuthAccess")
public class AWSS3AuthAccessResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public AWSAccessAuthDTO getAWSAccessAuthDetails() {
		return AWSAccessAuthProvider.getAWSAccessAuthDetails();
	}
}
