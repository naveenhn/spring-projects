/**
 * 
 */
package com.sarvah.mbg.userprofile.auth.exception;

/**
 * @author naveen
 *
 */
public class TokenExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenExpiredException() {
		// TODO Auto-generated constructor stub
	}

	public TokenExpiredException(String message) {
		super(message);
	}

}
