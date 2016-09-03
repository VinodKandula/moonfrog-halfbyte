/**
 * 
 */
package com.aws.sns.resource.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

/**
 * @author Vinod
 *
 */
public class RestAuthenticationTokenFilter implements Filter {

	@Override
	public void destroy() {

	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);

		String authToken = this.extractAuthTokenFromRequest(httpRequest);
		String userUUID = TokenUtils.getUserUUIDFromToken(authToken);

		if (userUUID != null) {
			if (TokenUtils.validateToken(authToken, userUUID)) {
				chain.doFilter(request, response);
			} else {
				HttpServletResponse httpResponse = this.getAsHttpResponse(response);
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				throw new WebApplicationException(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}

		
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}
		return (HttpServletRequest) request;
	}
	
	private HttpServletResponse getAsHttpResponse(ServletResponse response) {
		if (!(response instanceof HttpServletResponse)) {
			throw new RuntimeException("Expecting an HTTP response");
		}
		return (HttpServletResponse) response;
	}

	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Auth-Token");

		/* If token not found get it from request parameter */
		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
