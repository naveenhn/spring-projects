/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class UserLoginRequestParam.
 *
 * @author naveen
 */
public class UserLoginRequestParam {

	/** The username. */
	@NotNull
	private String username;

	/** The password. */
	@NotNull
	private String password;

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Validate.
	 *
	 * @return true, if successful
	 */
public boolean validate() {
		
		if (!EmailValidator.getInstance().isValid(getUsername())) {
			throw new ValidationException("username is not a valid email address");
		}
		
		if (StringUtils.isBlank(getPassword())) {
			throw new ValidationException("password is blank");
		}

		return true;
	}



}
