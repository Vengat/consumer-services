/**
 * 
 */
package com.bang.controller.exception;

/**
 * @author vengat.r
 *
 */
public class CustomerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CustomerNotFoundException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public CustomerNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

}
