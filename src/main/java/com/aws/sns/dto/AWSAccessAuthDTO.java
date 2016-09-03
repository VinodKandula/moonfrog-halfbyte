/**
 * 
 */
package com.aws.sns.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vinod
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AWSAccessAuthDTO {
	
	protected String policy;
	protected String key;
	protected String signature;
	
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

}
