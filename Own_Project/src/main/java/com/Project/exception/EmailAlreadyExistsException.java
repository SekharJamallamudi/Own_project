package com.Project.exception;

public class EmailAlreadyExistsException extends Exception {
	   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException(String message)
	{
		super(message);
	}
}