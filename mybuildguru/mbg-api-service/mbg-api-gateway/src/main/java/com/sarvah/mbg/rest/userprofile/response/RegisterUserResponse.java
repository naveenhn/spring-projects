/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.response;

import javax.ws.rs.core.Response.Status;

import com.sarvah.mbg.rest.model.MessageResponse;
import com.sarvah.mbg.userprofile.auth.model.ApiUser;

/**
 * @author naveen
 *
 */
public class RegisterUserResponse extends MessageResponse {

	private ApiUser user;

	public RegisterUserResponse(ApiUser apiUser, Status status, int messageCode, String message) {
		super(status, messageCode, message, status.getReasonPhrase());
		this.user = apiUser;

	}


	/**
	 * @return the user
	 */
	public ApiUser getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(ApiUser user) {
		this.user = user;
	}

}
