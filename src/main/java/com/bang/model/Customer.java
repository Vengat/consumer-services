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
	
	@OneToMany(mappedBy = "customer", targetEntity = Job.class, fetch=FetchType.EAGER)
	private List<Job> jobs;
	
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

}
