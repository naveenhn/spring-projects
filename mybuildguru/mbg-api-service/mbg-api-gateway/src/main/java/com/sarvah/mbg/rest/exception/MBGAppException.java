/**
 * 
 */
package com.sarvah.mbg.rest.exception;

import javax.ws.rs.WebApplicationException;

/**
 * @author naveen
 *
 */
public class MBGAppException extends WebApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8216629247055666205L;
	private int status;
	private int code;
	private String message;
	private String developerMessage;
	
	/**
	 * Constructor
	 * @param t
	 * @param status
	 * @param code
	 * @param devMsg
	 */
	public MBGAppException(String message, Throwable t,String devMsg, int status, int code) {
		super(t);
		this.status = status;
		this.code = code;
		this.message = message;
		this.developerMessage = devMsg;
		
	}
	
	public MBGAppException(String message, int status, int code) {
		this.message = message;
		this.status = status;
		this.code = code;
	}

	/**
	 * Constructor
	 * @param t
	 * @param status
	 * @param code
	 */
	public MBGAppException(String message, Throwable t, int status, int code) {
		this(message, t,t.getMessage(), status, code);
	}


	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}


	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the developerMessage
	 */
	public String getDeveloperMessage() {
		return developerMessage;
	}

	/**
	 * @param developerMessage the developerMessage to set
	 */
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	
}
