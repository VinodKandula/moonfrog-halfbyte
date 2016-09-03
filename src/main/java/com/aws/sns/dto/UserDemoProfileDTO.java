/**
 * 
 */
package com.aws.sns.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Vinod
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(UserDTO.class)
public class UserDemoProfileDTO extends UserDTO {

	protected int udid;
	protected int uid;
	protected String name;
	protected String firstName;
	protected String lastName;
	protected String gender;
	protected int age;
	protected String birthDate;
	protected String maritalStatus;
	protected String email;
	protected String location;
	protected String loginType;
	protected String loginId;
	protected String profileImageUrl;
	protected String phone;
	
	public int getUdid() {
		return udid;
	}
	public void setUdid(int udid) {
		this.udid = udid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "UserProfileDTO [udid=" + udid + ", name=" + name
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", age=" + age + ", birthDate="
				+ birthDate + ", maritalStatus=" + maritalStatus + ", email="
				+ email + ", location=" + location + ", loginType=" + loginType
				+ ", loginId=" + loginId + ", profileImageUrl="
				+ profileImageUrl + ", uid=" + uid + ", phone=" + phone
				+ ", deviceUUID=" + deviceUUID + "]";
	}
	
}
