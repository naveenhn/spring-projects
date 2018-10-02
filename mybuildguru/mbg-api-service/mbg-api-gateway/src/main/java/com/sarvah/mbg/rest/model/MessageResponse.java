/**
 * 
 */
package com.sarvah.mbg.rest.model;

import javax.ws.rs.core.Response;

/**
 * @author naveen
 *
 */
public class MessageResponse extends AbstractResponse {

	public MessageResponse(Response.Status status, int messageCode, String message, String developerMessage) {
		this.setStatus(status.getStatusCode());
		this.setCode(messageCode);
		this.setMessage(message);
		this.setDeveloperMessage(developerMessage);
	}

}
