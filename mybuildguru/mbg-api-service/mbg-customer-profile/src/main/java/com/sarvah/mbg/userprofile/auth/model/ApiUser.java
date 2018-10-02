/**
 * 
 */
package com.sarvah.mbg.userprofile.auth.model;

import java.security.Principal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author naveen
 *
 */
public class ApiUser implements Principal {

	@Email
	@NotNull
	private String username; // email address is the username for our system
	private String firstName;
	private String lastName;
	private String userPhoneNumber;
	private String uid;
	private String role;
	private String token;
	private long expires;
	private String mbgRoles;

	public ApiUser() {
	}

	public ApiUser(UserInfo user) {
		this(user, user.getMongoUserId());
	}

	public ApiUser(UserInfo user, String userId) {
		this.username = user.getUsername();
		this.firstName = user.getFirstname();
		this.lastName = user.getLastname();
		this.uid = userId;
		this.role = user.getRole().toString();
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	@JsonIgnore
	public String getName() {
		// TODO Auto-generated method stub
		return this.username;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the expires
	 */
	public long getExpires() {
		return expires;
	}

	/**
	 * @param expires
	 *            the expires to set
	 */
	public void setExpires(long expires) {
		this.expires = expires;
	}

	/**
	 * @return the mbgRoles
	 */
	public String getMbgRoles() {
		return mbgRoles;
	}

	/**
	 * @param mbgRoles
	 *            the mbgRoles to set
	 */
	public void setMbgRoles(String mbgRoles) {
		this.mbgRoles = mbgRoles;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this);
	}

}
