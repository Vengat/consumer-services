package com.bang.controller.exception;

public class ServiceProviderNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4744246241540765891L;
	
	public ServiceProviderNotFoundException() {
	    super();	
	}
	
	public ServiceProviderNotFoundException(String message) {
		super(message);
	}
	
	public ServiceProviderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
