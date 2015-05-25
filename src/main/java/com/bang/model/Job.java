package com.bang.model;

import java.util.Date;

import com.bang.misc.*;

/*import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;*/
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;


@Entity
@Table(name = "jobs")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private JobType jobType; //plumbing, painting etc
	@Enumerated(EnumType.STRING)
	private JobStatus jobStatus;
	
	@Column(name = "customer_name", nullable = false, length = 50)
	private String customerName;
	
	@Column(name = "service_provider_name", length = 50)
	private String serviceProviderName;
	
	@Column(name = "pincode", length = 10, nullable = false)
	private String pincode;
	
	@Column(name = "date_initiated", nullable = false)
	//@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@Type(type = "date")
	private Date dateInitiated;
	@Column(name = "date_done", nullable = true)
	@Type(type = "date")
	private Date dateDone;
	
	@Column(name = "description", length = 500)
	private String description;
	
	protected Job() {
	}
	
	public Job(long id, JobType jobType, JobStatus jobStatus, String customerName, String pincode, String description,  Date dateInitiated) {
		this.id = id;
		this.jobType = jobType;
		this.customerName = customerName;
		this.pincode = pincode;
		this.jobStatus = jobStatus;
		this.description = description;
		this.dateInitiated = dateInitiated;
	}
	
	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Date getDateDone() {
		return dateDone;
	}

	public void setDateDone(Date dateDone) {
		this.dateDone = dateDone;
	}

	public long getId() {
		return id;
	}

	public Date getDateInitiated() {
		return dateInitiated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
