/**
 * 
 */
package com.bang.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bang.controller.exception.ServiceProviderNotFoundException;
import com.bang.dao.ServiceProviderRepository;
import com.bang.misc.JobStatus;
import com.bang.model.Job;
import com.bang.model.ServiceProvider;

/**
 * @author vengat.r
 *
 */
@Service
public class ServiceProviderService {
	
	private static final Logger logger = Logger.getLogger(ServiceProviderService.class);
	
	@Autowired
	ServiceProviderRepository repository;
	
	@Autowired
	JobService jobService;
	
	public ServiceProvider create(ServiceProvider serviceProvider) {
		return repository.save(serviceProvider);
	}
	
	public ServiceProvider getById(long id) {
		ServiceProvider sp = repository.findOne(id);
		if (sp == null) throw new ServiceProviderNotFoundException("ServiceProvider was not found with id "+id);
		return sp;
	}
	
	public ServiceProvider getByMobileNumber(long mobileNumber) {
		ServiceProvider sp =  repository.findByMobileNumber(mobileNumber);
		if (sp == null) throw new ServiceProviderNotFoundException("ServiceProvider was not found with mobileNumber "+mobileNumber);
		return sp;
	}
	
	public ServiceProvider update(ServiceProvider serviceProvider) {
		ServiceProvider sp = getById(serviceProvider.getId());
		if (sp == null) throw new ServiceProviderNotFoundException("ServiceProvider was not found with id "+serviceProvider.getId());
		if (!sp.getName().equals(serviceProvider.getName())) sp.setName(serviceProvider.getName());
		if (sp.getMobileNumber() != serviceProvider.getMobileNumber()) sp.setMobileNumber(serviceProvider.getMobileNumber());
		if (serviceProvider.getJobTypes() != null && !sp.getJobTypes().equals(serviceProvider.getJobTypes())) sp.setJobTypes(serviceProvider.getJobTypes());
		return repository.save(sp);
	}
	
	public List<ServiceProvider> getAll() {
		return (List<ServiceProvider>) repository.findAll();	
	}
	
	public List<Job> getJobsByMobileNumberAndStatus(long mobileNumer, JobStatus jobStatus) {
		return (List<Job>) jobService.getBySPMobileNumberAndStatus(mobileNumer, jobStatus);
	}
	
	public List<Job> getJobsByPincodeAndStatus(JobStatus jobStatus, String pincode) {
		return (List<Job>) jobService.getByPincodeAndStatus(jobStatus, pincode);
	}
	
	public Job closeJob(long id) {
		return jobService.closeJob(jobService.getJobById(id));
	}

}
