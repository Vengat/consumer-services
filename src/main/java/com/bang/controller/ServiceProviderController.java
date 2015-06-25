/**
 * 
 */
package com.bang.controller;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;











import com.bang.controller.exception.JobNotFoundException;
import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
import com.bang.model.Job;
import com.bang.model.ServiceProvider;
import com.bang.service.JobService;
import com.bang.service.ServiceProviderService;

/**
 * @author vengat.r
 *
 */
@RestController
public class ServiceProviderController {
	
	private static final Logger logger = Logger.getLogger(JobController.class);
	
	@Autowired
	ServiceProviderService service;
	
	@Autowired
	JobService jobService;
	
	@RequestMapping(value = "/serviceProviders", method = RequestMethod.GET)
	public ResponseEntity<ServiceProvider> get() {
		ServiceProvider serviceProvider = new ServiceProvider(123456789L, "Service Provider Name", EnumSet.allOf(JobType.class));
		return new ResponseEntity<ServiceProvider>(serviceProvider, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceProviders", method = RequestMethod.POST)
	public ResponseEntity<ServiceProvider> create(@RequestBody ServiceProvider serviceProvider) {
		ServiceProvider sp = service.create(serviceProvider);
		return new ResponseEntity<ServiceProvider>(sp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceProviders", method = RequestMethod.PUT)
	public ResponseEntity<ServiceProvider> update(@RequestBody ServiceProvider serviceProvider) {
		ServiceProvider sp = service.update(serviceProvider);
		return new ResponseEntity<ServiceProvider>(sp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceProviders/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<ServiceProvider> get(@PathVariable("id") long id) {
		ServiceProvider sp = service.getById(id);
		return new ResponseEntity<ServiceProvider>(sp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceProviders/all", method = RequestMethod.GET)
	public ResponseEntity<List<ServiceProvider>> getAll() {
		return new ResponseEntity<List<ServiceProvider>>(service.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceProviders/mobileNumber/{mobileNumber}", method = RequestMethod.GET)
	public ResponseEntity<ServiceProvider> getByMobileNumber(@PathVariable("mobileNumber") long mobileNumber) {
		return new ResponseEntity<ServiceProvider>(service.getByMobileNumber(mobileNumber), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceProviders/assignedJobs/mobileNumber/{mobileNumber}/", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getAssignedJobsByServiceProviderMobileNumber(@PathVariable("mobileNumber") long mobileNumber) {
		List<Job> jobs = service.getJobsByMobileNumberAndStatus(mobileNumber, JobStatus.ASSIGNED);
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceProviders/openJobs/pincode/{pincode}", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getJobsByPincode(@PathVariable("pincode") String pincode) {
		List<Job> jobs = service.getJobsByPincodeAndStatus(JobStatus.OPEN, pincode);
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceProviders/closeJob/jobId/{jobId}", method = RequestMethod.PUT)
	public ResponseEntity<Job> closeJob(@PathVariable("jobId") long jobId) {
		Job job = service.closeJob(jobId);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/serviceProviders/assignJob/jobId/{jobId}", method = RequestMethod.PUT)
	public ResponseEntity<Job> assignJob(@PathVariable("jobId") long jobId, @RequestBody ServiceProvider serviceProvider) {
		Job job = service.assignJob(jobId, serviceProvider);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	

}
