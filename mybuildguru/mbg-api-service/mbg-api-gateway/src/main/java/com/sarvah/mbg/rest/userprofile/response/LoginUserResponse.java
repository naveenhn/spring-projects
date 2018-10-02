/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.response;

import javax.ws.rs.core.Response.Status;

import com.sarvah.mbg.userprofile.auth.model.ApiUser;

/**
 * @author naveen
 *
 */
public class LoginUserResponse extends RegisterUserResponse {

	public LoginUserResponse(ApiUser apiUser, Status status, int messageCode, String message) {
		super(apiUser, status, messageCode, message);
	}

}
