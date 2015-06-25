/**
 * 
 */
package com.bang.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * @author vengat.r
 *
 */
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CUST_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "customer_name", nullable = false, length = 50)
	private String name;
	
	@Column(name = "pincode", nullable = false, length = 6)
	//@Length(min = 6, max = 6)
	private String pincode;
	
	@Column(name = "address", length = 500)
	private String address;
	
	@Column(name = "mobile_number", nullable = false, unique = true)
	private long mobileNumber;
	
	@Column(name = "city")
	private String city;
	
	//@OneToMany(mappedBy = "customer", targetEntity = Job.class, fetch=FetchType.LAZY)
	//private List<Job> jobs;
	
	protected Customer() {}
	
	public Customer(String name, String address, String city, String pincode, long mobileNumber) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.pincode = pincode;
		this.mobileNumber = mobileNumber;
	}
	
	public Customer(String name, String pincode, long mobileNumber) {
		this.name = name;
		this.pincode = pincode;
		this.mobileNumber = mobileNumber;
	}
	
	@Override
	public String toString() {
	    return String.format("Customer name %s, address %s, city %s, pincode %s, mobileNumber %d%n", this.name, this.address, this.city, this.pincode, this.mobileNumber);	
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		
		if (!(o instanceof Customer)) return false;
		
		Customer cust = (Customer) o;
		
		if (!cust.getAddress().equals(this.address)) return false;
		
		if(!cust.getCity().equals(this.city)) return false;
		
		if (cust.getMobileNumber() != this.mobileNumber) return false;
		
		if (!cust.getName().equals(this.name)) return false;
		
		if (!cust.getPincode().equals(this.pincode)) return false;
		
		if(cust.getId() != this.id) return false;
		
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getId() {
		return id;
	}
	
	/*
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
	public List<Job> getJobs() {
		return this.jobs;
	}
	*/

}
