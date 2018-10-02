/**
 * 
 */
package com.sarvah.mbg.userprofile.auth.exception;

/**
 * @author naveen
 *
 */
public class UserCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8071524800436791372L;

	public UserCreationException(String message) {
		super(message);
	}

	public UserCreationException(String message, Throwable t) {
		super(message, t);
	}

}
