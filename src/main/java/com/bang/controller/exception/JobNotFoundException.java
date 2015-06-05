/**
 * 
 */
package com.bang.controller.exception;

/**
 * @author vengat.r
 *
 */
public class JobNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7773298334318520453L;
	
	public JobNotFoundException() {
		super();
	}
	
	public JobNotFoundException(String message) {
		super(message);
	}
	
	public JobNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
