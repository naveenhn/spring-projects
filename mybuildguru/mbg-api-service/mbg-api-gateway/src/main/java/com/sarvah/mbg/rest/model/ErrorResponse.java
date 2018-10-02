/**
 * 
 */
package com.sarvah.mbg.rest.model;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.springframework.beans.BeanUtils;

import com.sarvah.mbg.rest.exception.MBGAppException;

/**
 * @author naveen
 *
 */
public class ErrorResponse extends AbstractResponse {
	
	public ErrorResponse(MBGAppException ex) {
		BeanUtils.copyProperties(ex, this);
		this.setLink("https://api.mbg.com/error/codes");
	}
	
	public ErrorResponse(NotFoundException ex) {
		this.setStatus(Response.Status.NOT_FOUND.getStatusCode());
		this.setMessage(ex.getLocalizedMessage());
		this.setDeveloperMessage(ex.getMessage());
		this.setLink("https://jersey.java.net/apidocs/latest/jersey/javax/ws/rs/NotFoundException.html");
	}
	
}
