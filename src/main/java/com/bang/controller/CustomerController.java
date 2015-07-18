/**
 * 
 */
package com.bang.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bang.controller.exception.CustomerExistsException;
import com.bang.controller.exception.CustomerNotFoundException;
import com.bang.controller.exception.JobNotFoundException;
import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
import com.bang.model.Customer;
import com.bang.model.Job;
import com.bang.service.CustomerService;

/**
 * @author vengat.r
 *
 */

@RestController
public class CustomerController {
	
	private static final Logger logger = Logger.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService service;
	
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public ResponseEntity<Customer> get() {
		logger.info("Customer Controller get() called");
		Customer customer = new Customer("Name", "Address", "City", "Pincode", 987654321);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public ResponseEntity<Customer> post(@RequestBody Customer customer) throws CustomerExistsException {
		Customer cust = service.create(customer);
		return new ResponseEntity<Customer>(cust, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/customers/mobileNumber/{mobileNumber}", method = RequestMethod.GET)
	public ResponseEntity<Customer> getByMobileNumber(@PathVariable long mobileNumber) throws NullPointerException {
		Customer customer = service.getByMobileNumber(mobileNumber);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/customers/jobs/mobileNumber/{mobileNumber}", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getAllJobs(@PathVariable long mobileNumber) {
		List<Job> jobs = service.getAllJobsByMobileNumber(mobileNumber);
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/customers", method = RequestMethod.PUT)
	public ResponseEntity<Customer> update(@RequestBody Customer customer) throws CustomerNotFoundException, CustomerExistsException {
        Customer cust = service.update(customer);
		return new ResponseEntity<Customer>(cust, HttpStatus.OK);
	}
	
    @RequestMapping(value = "/customers/cancelJob/jobId/{jobId}/mobileNumber/{mobileNumber}", method = RequestMethod.PUT)
	public ResponseEntity<Job> cancelJob(@PathVariable("jobId") long jobId, @PathVariable("mobileNumber") long mobileNumber) throws JobNotFoundException {
		Job j = service.cancelJob(jobId, mobileNumber);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/customers/agreeJob", method = RequestMethod.PUT)
    public ResponseEntity<Job> agreeServiceProviderForJob(@RequestBody Job job) throws NullPointerException, IllegalStateException {
    	Job j = service.agreeServiceProviderForJob(job);
    	return new ResponseEntity<Job>(j, HttpStatus.OK);    	
    }
    
    @RequestMapping(value = "/customers/unassignJob", method = RequestMethod.PUT)
    public ResponseEntity<Job> unassignServiceProviderForJob(@RequestBody Job job) throws NullPointerException, IllegalStateException {
    	Job j = service.unassignServiceProviderForJob(job);
    	return new ResponseEntity<Job>(j, HttpStatus.OK);    	
    }
    
    

}
