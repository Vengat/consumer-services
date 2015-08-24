package com.bang.controller.exception;

public class CouponExistsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponExistsException() {
		super();
	}
	
	public CouponExistsException(String message) {
		super(message);
	}
	
	public CouponExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
