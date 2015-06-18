/**
 * 
 */
package com.bang.misc;

/**
 * @author Vengat
 *
 */
public enum JobType {
	PLUMBING("plumbing"),
	ELECTRICAL("electrical"),
	UNDEFINED("undefined");

	String val;
	JobType(String val) {
		this.val = val;
	}

	String getVal() {
		return this.val;
	}
}
