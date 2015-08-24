package com.bang.misc;

public class City {
	
	private String name;
	private String state;
	private String country;
	private String pincode;
	
	public City(String name, String state, String country, String pincode) {
		this.name = name;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

}
