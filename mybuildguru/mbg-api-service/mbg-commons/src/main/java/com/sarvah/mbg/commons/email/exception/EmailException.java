package com.sarvah.mbg.commons.email.exception;

public class EmailException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public EmailException(String message, Throwable t,String devMsg, int status, int code)
	{
		this.status = status;
		this.code = code;
		this.message = message;
		this.developerMessage = devMsg;
	}
	
	/**
	 * Constructor
	 * @param t
	 * @param status
	 * @param code
	 */
	public EmailException(String message, Throwable t, int status, int code) {
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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

}