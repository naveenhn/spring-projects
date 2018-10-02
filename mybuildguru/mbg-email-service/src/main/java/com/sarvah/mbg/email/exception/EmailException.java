package com.sarvah.mbg.email.exception;

public class EmailException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EmailException()
	{
		System.out.println("Email not sent because of exception");
	}
}
