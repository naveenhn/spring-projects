/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

/**
 * @author naveen
 *
 */
public class UserResetPasswordRequestParam {
	
	private String username,password;

	

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
