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









import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
import com.bang.model.Job;
import com.bang.model.ServiceProvider;
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

}
