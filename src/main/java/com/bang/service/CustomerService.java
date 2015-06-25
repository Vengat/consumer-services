/**
 * 
 */
package com.bang.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bang.controller.JobController;
import com.bang.controller.exception.CustomerExistsException;
import com.bang.controller.exception.CustomerNotFoundException;
import com.bang.controller.exception.JobNotFoundException;
import com.bang.dao.CustomerRepository;
import com.bang.misc.JobStatus;
import com.bang.model.Customer;
import com.bang.model.Job;

/**
 * @author vengat.r
 *
 */
@Service
public class CustomerService {
	
	private static final Logger logger = Logger.getLogger(CustomerService.class);
	
	@Autowired
	CustomerRepository repository;
	
	@Autowired
	JobService jobService;
	
	public Customer create(Customer customer) {
		//if (getByMobileNumber(customer.getMobileNumber()) != null) return getByMobileNumber(customer.getMobileNumber());
		if (isCustomerExists(customer.getMobileNumber())) throw new CustomerExistsException("Exists. Customer with mobile number "+customer.getMobileNumber());
		return repository.save(customer);
	}
	
	@Transactional
	public Customer update(Customer customer) {
		if (!isCustomerExists(customer.getMobileNumber())) throw new CustomerNotFoundException("Not found. Customer with mobile number "+customer.getMobileNumber());
		Customer cust = getByMobileNumber(customer.getMobileNumber());
		if (cust.equals(customer)) throw new CustomerExistsException("Matches existing customer fully. Customer with mobile number "+customer.getMobileNumber());
		if (customer.getAddress() != null && cust.getAddress() != customer.getAddress()) cust.setAddress(customer.getAddress());
		if (customer.getCity() != null && cust.getCity() != customer.getCity()) cust.setCity(customer.getCity());
		if (customer.getName() != null && !cust.getName().equals(customer.getName())) cust.setName(customer.getName());
		if (customer.getPincode() != null && !cust.getPincode().equals(customer.getPincode())) cust.setPincode(customer.getPincode());
		return customer;
	}
	
	public boolean isCustomerExists(long mobileNumber) {
		if (this.getByMobileNumber(mobileNumber) != null) return true;
		return false;
	}
	
	public Customer getByMobileNumber(long mobileNumber) {
		return repository.findByMobileNumber(mobileNumber);
	}
	
	public List<Job> getJobsByMobileNumberAndStatus(long mobileNumber, JobStatus jobStatus) {
		List<Job> jobs = jobService.getByCustomerMobileNumberAndStatus(mobileNumber, jobStatus);
		return jobs;
	}
	
	public List<Job> getOpenJobsByMobileNumber(long mobileNumber) {
		return this.getJobsByMobileNumberAndStatus(mobileNumber, JobStatus.OPEN);
	}
	
	public List<Job> getAssignedJobsByMobileNumber(long mobileNumber) {
		return this.getJobsByMobileNumberAndStatus(mobileNumber, JobStatus.ASSIGNED);
	}
	
	public List<Job> getWIPJobsByMobileNumber(long mobileNumber) {
		return this.getJobsByMobileNumberAndStatus(mobileNumber, JobStatus.WIP);
	}
	
	public List<Job> getClosedJobsByMobileNumber(long mobileNumber) {
		return this.getJobsByMobileNumberAndStatus(mobileNumber, JobStatus.CLOSED);
	}
	
	public List<Job> getAllJobsByMobileNumber(long mobileNumber) {
		List<Job> allJobs = new ArrayList<Job>();
		allJobs.addAll(getOpenJobsByMobileNumber(mobileNumber));
		allJobs.addAll(getAssignedJobsByMobileNumber(mobileNumber));
		allJobs.addAll(getWIPJobsByMobileNumber(mobileNumber));
		allJobs.addAll(getClosedJobsByMobileNumber(mobileNumber));
		return allJobs;
	}
	
	public Job cancelJob(long jobId, long mobileNumber) throws JobNotFoundException, IllegalArgumentException, NullPointerException {
		Job j = jobService.getJobById(jobId);
		if (j.getCustomerMobileNumber() != mobileNumber) {
			throw new JobNotFoundException("Either the job does not exist or the customer does not own it");
		} else {
			return jobService.cancel(j);
		}		
	}
}
