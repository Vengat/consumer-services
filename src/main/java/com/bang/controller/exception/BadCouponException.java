package com.bang.controller.exception;

public class BadCouponException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadCouponException() {
		super();
	}
	
	public BadCouponException(String message) {
		super(message);
	}
	
	public BadCouponException(String message, Throwable cause) {
		super(message, cause);
	}
}
