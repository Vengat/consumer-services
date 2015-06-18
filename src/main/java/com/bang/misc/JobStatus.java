/**
 * 
 */
package com.bang.misc;

/**
 * @author Vengat
 *
 */
public enum JobStatus {
	OPEN("open"),
	ASSIGNED("assigned"),
    WIP("wip"),
    CANCELLED("cancelled"),
    CLOSED("closed");

    String val;
    JobStatus(String val) {
        this.val = val;
    }

    String getVal() {
        return val;
    }
}
