package com.bang.misc;

public enum DaySegment {
	MORNING("9-11"),
	FORENOON("11-1"),
	AFTERNOON("1-3"),
	EVENING("3-5");
	
	String daySegment;
	DaySegment(String daySegment) {
		this.daySegment = daySegment;
	}
	
	public String getDaySegment() {
		return this.daySegment;
	}
}
