/**
 * 
 */
package com.bang.controller.exception;

import com.bang.model.Customer;

/**
 * @author vengat.r
 *
 */
public class CustomerExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7514857956112415007L;

	/**
	 * 
	 */
	
	public CustomerExistsException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public CustomerExistsException(String message) {
        super(message); 
	}
	
	public CustomerExistsException(String message, Throwable cause) {
        super(message, cause); 
	}

}
