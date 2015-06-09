package com.bang.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bang.controller.exception.CustomerExistsException;
import com.bang.controller.exception.JobNotFoundException;
import com.bang.dao.JobRepository;
import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
import com.bang.model.Customer;
import com.bang.model.Job;

@Service
public class JobService {
	
	private static final Logger logger = Logger.getLogger(JobService.class);
	
	@Autowired
	JobRepository repository;
	
	@Autowired
	ServiceProviderService spService;
	
	@Autowired
	CustomerService customerService;
	
	public Job create(Job job) throws IllegalArgumentException {
		logger.info("***This log is from the job service layer***");
		if (job.getJobType().equals(JobType.UNDEFINED)) throw new IllegalArgumentException("Job cannot be undefined");
	    if (job.getPincode().equals("Pincode")) throw new IllegalArgumentException("Pincode needed");
	    createCustomer(job);
	    return repository.save(job);
	}
	
	public void createCustomer(Job job) {
		try {
			customerService.create(new Customer(job.getCustomerName(), job.getPincode(), job.getCustomerMobileNumber()));
		} catch(CustomerExistsException e) {
			
		}		
	}
	
	public Job update(Job job) throws IllegalArgumentException {
		Job j = repository.findOne(job.getId());
		if (j == null) throw new NullPointerException("Job not found");
		if (!j.getJobType().equals(job.getJobType())) {
			if (spService.getByMobileNumber(job.getServiceProviderMobileNumber()) != null) {
				if (!spService.getByMobileNumber(job.getServiceProviderMobileNumber()).getJobTypes().contains(job.getJobType())) throw new IllegalArgumentException("Job type is not provided by the ServiceProvider");
				j.setJobType(job.getJobType());	
			}			
		}
		if (!j.getJobStatus().equals(job.getJobStatus())) j.setJobStatus(job.getJobStatus());
		if (j.getDateDone() == null || job.getDateDone() != null && j.getDateDone().before(job.getDateDone())) j.setDateDone(job.getDateDone());
		if (!j.getCustomerName().equals(job.getCustomerName())) j.setCustomerName(job.getCustomerName());
		if (job.getDescription().isEmpty() || !j.getDescription().equalsIgnoreCase(job.getDescription())) j.setDescription(job.getDescription());
		if (!j.getPincode().equals(job.getPincode())) j.setPincode(job.getPincode());
		
		if (j.getServiceProviderMobileNumber() != job.getServiceProviderMobileNumber() && spService.getByMobileNumber(job.getServiceProviderMobileNumber()) != null) {
			j.setServiceProviderMobileNumber(job.getServiceProviderMobileNumber());
			j.setServiceProviderName(spService.getByMobileNumber(job.getServiceProviderMobileNumber()).getName());
		}
		//if (j.getServiceProviderName() == null || !j.getServiceProviderName().equals(job.getServiceProviderName())) j.setServiceProviderName(job.getServiceProviderName());	    
		return repository.save(j);       	
	}
	
	public Job getJobById(long id) {
		Job j = repository.findOne(id);	
		if (j == null) throw new JobNotFoundException("Job not found for the id ->"+id);
		return j;
	}
	
	public List<Job> getAllJobs() {
		return (List<Job>) repository.findAll();
	}
	
	public List<Job> getByPincode(String pincode) {
		return (List<Job>) repository.findJobsByPincode(pincode);
	}
	
	public List<Job> getByPincodeAndStatus(JobStatus jobStatus, String pincode) {
		List<Job> jobs = repository.findJobsByJobStatusAndPincode(jobStatus, pincode);
		return jobs;
	}
	
	public List<Job> getByTypeStatusAndPincode(JobType jobType, JobStatus jobStatus, String pincode) {
		List<Job> jobs = repository.findJobsByJobTypeAndJobStatusAndPincode(jobType, jobStatus, pincode);
		return jobs;
	}
	
	public List<Job> getBySPMobileNumberAndStatus(long serviceProviderMobileNumber, JobStatus jobStatus) {
		List<Job> jobs = repository.findJobsByServiceProviderMobileNumberAndJobStatus(serviceProviderMobileNumber, jobStatus);
		return jobs;
	}
	
	public List<Job> getByCustomerMobileNumberAndStatus(long customerMobileNumber, JobStatus jobStatus) {
		List<Job> jobs = repository.findJobsByCustomerMobileNumberAndJobStatus(customerMobileNumber, jobStatus);
		return jobs;
	}
}
