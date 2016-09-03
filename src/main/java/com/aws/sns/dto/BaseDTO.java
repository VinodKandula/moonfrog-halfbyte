/**
 * 
 */
package com.aws.sns.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Vinod
 *
 */
public class BaseDTO {

	protected String createUser;
	protected Date createTs;
	protected String updateUser;
	protected Date updateTs;

	@XmlTransient
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@XmlTransient
	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	@XmlTransient
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@XmlTransient
	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}
}
