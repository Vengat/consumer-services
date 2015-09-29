package com.bang.model;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

//import com.bang.misc.*;

/*import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;*/
import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bang.misc.DaySegment;
import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
import com.bang.model.Customer;
import com.bang.model.ServiceProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;


@Entity
@Table(name = "jobs")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Job implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "JOB_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private JobType jobType; //plumbing, painting etc
	@Enumerated(EnumType.STRING)
	private JobStatus jobStatus;
	@Enumerated(EnumType.STRING)
	private DaySegment daySegment;
	
	private TimeZone timeZone;

	@Column(name = "customer_name", nullable = false, length = 50)
	private String customerName;
	
	@Column(name = "customer_mobile_number", nullable = false)
	private long customerMobileNumber;
	
	@Column(name = "service_provider_name", length = 50, nullable = true)
	private String serviceProviderName;
	
	@Column(name = "sp_mobile_number", nullable = true)
	private long serviceProviderMobileNumber;
	
	@Column(name = "pincode", length = 10, nullable = false)
	private String pincode;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "customer_mobile_number", referencedColumnName = "mobile_number",  insertable = false, updatable = false, unique = true, nullable = false)
	private Customer customer;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)	
	@JoinColumn(name = "fk_mobile_number", referencedColumnName = "mobile_number",  insertable = false, updatable = false)
	private ServiceProvider serviceProvider;
	
	//@JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID", insertable = false, updatable = false)
	@JoinColumn(name = "JOB_ID", referencedColumnName = "INV_JOB_ID", insertable = false, updatable = false, nullable = false, unique = true)
	@OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = Invoice.class)
	private Invoice invoice;
	
/*	@Column(name = "invoice_id", nullable = true)
	private long invoiceId;
*/
	@JsonSerialize(using = DateTimeSerializer.class)
	@Column(name = "date_initiated", nullable = false)
	//@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	//@Type(type = "date")
	//@JsonProperty(value = "dtInitiated")
	private DateTime dateInitiated;
	
	@JsonSerialize(using = DateTimeSerializer.class)
	@Column(name = "date_preferred", nullable = true)
	//@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	//@Type(type = "date")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	//@JsonProperty(value = "dtPreferred")
	private DateTime datePreferred;
	
	@JsonSerialize(using = DateTimeSerializer.class)
	@Column(name = "start_time", nullable = true)
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	//@Type(type = "date")
	//@JsonProperty(value = "strtTime")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startTime;

	@JsonSerialize(using = DateTimeSerializer.class)
	@Column(name = "date_done", nullable = true)
	//@Type(type = "date")
	//@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	//@JsonProperty(value = "dtDone")
	private DateTime dateDone;
	
	@Column(name = "description", length = 500)
	private String description;
	
	protected Job() {
	}
	
	public Job(JobType jobType, JobStatus jobStatus, String customerName, String pincode, String description, long customerMobileNumber, long serviceProviderMobileNumber, String serviceProviderName, DateTime datePreferred, DaySegment daySegment) {
		//this.id = id;
		this.jobType = jobType;
		this.customerName = customerName;
		this.pincode = pincode;
		this.jobStatus = jobStatus;
		this.description = description;
		this.customerMobileNumber = customerMobileNumber;
		this.serviceProviderMobileNumber = serviceProviderMobileNumber;
		this.serviceProviderName = serviceProviderName;
		this.dateInitiated = new DateTime();
		this.datePreferred = datePreferred;
		this.daySegment = daySegment;
	}
	

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Job)) return false;
		Job job = (Job) o;
		if (job.getId() != this.id) return false;
		if (!job.getJobType().equals(this.jobType)) return false;
		//if (!job.getJobStatus().equals(this.jobStatus)) return false;
		if (!job.getPincode().equals(this.pincode)) return false;
		if (job.getCustomerMobileNumber() != this.customerMobileNumber) return false;
		if (!job.getCustomerName().equals(this.customerName)) return false;
		if (!job.getDateInitiated().equals(this.dateInitiated)) return false;
		return true;
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
	
	public DaySegment getDaySegment() {
		return daySegment;
	}

	public void setDaySegment(DaySegment daySegment) {
		this.daySegment = daySegment;
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

	public DateTime getDateDone() {
		return dateDone;
	}

	public void setDateDone(DateTime dateDone) {
		this.dateDone = dateDone;
	}

	public long getId() {
		return id;
	}

	public DateTime getDateInitiated() {
		return dateInitiated;
	}
	
	 public void setDateInitiated(DateTime dateInitiated) {
         this.dateInitiated = dateInitiated;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getServiceProviderMobileNumber() {
		return serviceProviderMobileNumber;
	}

	public void setServiceProviderMobileNumber(long serviceProviderMobileNumber) {
		this.serviceProviderMobileNumber = serviceProviderMobileNumber;
	}

	public long getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public void setCustomerMobileNumber(long customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	
	public DateTime getDatePreferred() {
		return datePreferred;
	}

	public void setDatePreferred(DateTime datePreferred) {
		this.datePreferred = datePreferred;
	}
	
	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	
/*	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
*/	
	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
}
