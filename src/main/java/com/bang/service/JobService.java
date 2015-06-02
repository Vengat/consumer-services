package com.bang.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bang.dao.JobRepository;
import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
import com.bang.model.Job;

@Service
public class JobService {
	
	@Autowired
	JobRepository repository;
	
	public Job create(Job job) {
	    return repository.save(job);
	}
	
	public Job update(Job job) {
		Job j = repository.findOne(job.getId());
		if (!j.getJobType().equals(job.getJobType())) j.setJobType(job.getJobType());
		if (!j.getJobStatus().equals(job.getJobStatus())) j.setJobStatus(job.getJobStatus());
		if (!j.getCustomerName().equals(job.getCustomerName())) j.setCustomerName(job.getCustomerName());
		if (j.getDateDone() == null || job.getDateDone() != null && j.getDateDone().before(job.getDateDone())) j.setDateDone(job.getDateDone());
		if (job.getDescription().isEmpty() || !j.getDescription().equalsIgnoreCase(job.getDescription())) j.setDescription(job.getDescription());
		if (!j.getPincode().equals(job.getPincode())) j.setPincode(job.getPincode());
		if (j.getServiceProviderName() == null || !j.getServiceProviderName().equals(job.getServiceProviderName())) j.setServiceProviderName(job.getServiceProviderName());
	    return repository.save(j);       	
	}
	
	public Job getJobById(long id) {
		return repository.findOne(id);
	}
	
	public List<Job> getAllJobs() {
		return (List<Job>) repository.findAll();
	}

}
