/**
 * 
 */
package com.bang.controller.exception;

/**
 * @author vengat.r
 *
 */
public class JobTypeUnmatchedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public JobTypeUnmatchedException() {
		// TODO Auto-generated constructor stub
	}
	
	public JobTypeUnmatchedException(String message) {
		super(message);
	}
	
	public JobTypeUnmatchedException(String message, Throwable cause) {
		super(message, cause);
	}

}
