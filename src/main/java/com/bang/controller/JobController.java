/**
 * 
 */
package com.bang.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	//private final AtomicLong counter = new AtomicLong();
	@Autowired
	JobService service;
	
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public ResponseEntity<Job> get() {
		Job job = new Job(0L, JobType.UNDEFINED, JobStatus.OPEN, "Your name", "Pincode", "job description",  new Date());
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
	
	@RequestMapping(value = "/jobs/{id}", method = RequestMethod.GET)
	public ResponseEntity<Job> get(@PathVariable("id") long id) {
		Job j = service.getJobById(id);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/jobs/all", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> getAll() {
		return new ResponseEntity<List<Job>>(service.getAllJobs(), HttpStatus.OK);
	}
}
