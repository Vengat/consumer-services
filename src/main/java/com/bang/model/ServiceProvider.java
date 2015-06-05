package com.bang.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bang.misc.JobType;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "serviceProviders")
public class ServiceProvider implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "mobile_number", unique = true, nullable = false)
	private long mobileNumber;
	
	@Column(nullable = false)
	private String name;
	
	@ElementCollection(targetClass = JobType.class)
	@CollectionTable(name = "jobTypes", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "job_types", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<JobType> jobTypes;
	
	@OneToMany(mappedBy = "serviceProvider", targetEntity = Job.class, fetch=FetchType.EAGER)
	private List<Job> jobs;
	
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
