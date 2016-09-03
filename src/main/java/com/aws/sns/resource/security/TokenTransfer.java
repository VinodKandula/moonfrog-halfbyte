/**
 * 
 */
package com.aws.sns.resource.security;

/**
 * @author Vinod
 *
 */
public class TokenTransfer {
	private final String token;

	public TokenTransfer(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}
