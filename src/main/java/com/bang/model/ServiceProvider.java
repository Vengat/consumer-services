package com.bang.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import com.bang.misc.JobType;

import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "serviceProviders")
public class ServiceProvider {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "mobile_number", unique = true)
	private long mobileNumber;
	
	@Column(nullable = false)
	private String name;
	
	@ElementCollection(targetClass = JobType.class)
	@CollectionTable(name = "jobTypes", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "job_types", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<JobType> jobTypes;
	
	protected ServiceProvider() {
		
	}
	
	public ServiceProvider(long mobileNumber, String name, Set<JobType> jobTypes) {
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.jobTypes = jobTypes;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<JobType> getJobTypes() {
		return jobTypes;
	}

	public void setJobTypes(Set<JobType> jobTypes) {
		this.jobTypes = jobTypes;
	}

	public long getId() {
		return id;
	}
	
	

}
