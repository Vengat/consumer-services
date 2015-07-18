/**
 * 
 */
package com.bang.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

import com.bang.misc.DaySegment;
import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
import com.bang.model.Job;
import com.bang.service.JobService;

/**
 * @author Vengat
 *
 */
@RestController
public class JobController {
	
	private static final Logger logger = Logger.getLogger(JobController.class);
	
	//private final AtomicLong counter = new AtomicLong();
	@Autowired
	JobService service;
	
	
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public ResponseEntity<Job> get() {
		logger.info("About to get job");
		Job job = new Job(JobType.UNDEFINED, JobStatus.OPEN, "", "", "", 0L, 0L, "", new Date(), DaySegment.EVENING);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/jobs", method = RequestMethod.POST)
	public ResponseEntity<Job> create(@RequestBody Job job) {
		Job j = service.create(job);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/jobs", method = RequestMethod.PUT)
	public ResponseEntity<Job> update(@RequestBody Job job) {
		Job j = service.update(job);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/jobs/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<Job> get(@PathVariable("id") long id) {
		Job j = service.getJobById(id);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/jobs/all", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getAll() {
		List<Job> jobs = service.getAllJobs();
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/jobs/pincode/{pincode}", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getByPincode(@PathVariable("pincode") String pincode) {
		List<Job> jobs = service.getByPincode(pincode);
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/jobs/pincode/{pincode}/status/{status}", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getByPincodeAndStatus(@PathVariable("status") JobStatus jobStatus, @PathVariable("pincode") String pincode) {
		List<Job> jobs = service.getByPincodeAndStatus(jobStatus, pincode);
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/jobs/pincode/{pincode}/status/{status}/type/{type}", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getByPincodeAndStatusAndType(@PathVariable("type") JobType jobType, @PathVariable("status") JobStatus jobStatus, @PathVariable("pincode") String pincode) {
		List<Job> jobs = service.getByTypeStatusAndPincode(jobType, jobStatus, pincode);
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/jobs/assignJob", method = RequestMethod.PUT)
	public ResponseEntity<Job> updateServiceProvider(@RequestBody Job job) {
		Job j = service.assign(job);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}
}
