/**
 * 
 */
package com.bang.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bang.controller.exception.ServiceProviderNotFoundException;
import com.bang.dao.ServiceProviderRepository;
import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
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
	
	public ServiceProvider create(ServiceProvider serviceProvider) throws IllegalArgumentException {
		try {
			if(getByMobileNumber(serviceProvider.getMobileNumber()) != null) throw new IllegalArgumentException("Service provider with the given mobile number already exists");
		}catch(ServiceProviderNotFoundException ex) {
			
		}		
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
	

	public boolean isServiceProvider(long mobileNumber) {
		ServiceProvider sp =  repository.findByMobileNumber(mobileNumber);
		if (sp == null) return false;
		return true;
	}
	

	@Transactional
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
	
	public List<ServiceProvider> getByPincodesServicedAndJobTypeSignedUpFor(String pincode, String jobType) {
	    return repository.findByPincodesServicedAndJobType(pincode, jobType);	
	}
	
	public List<Job> getJobsByMobileNumberAndStatus(long mobileNumer, JobStatus jobStatus) {
		return (List<Job>) jobService.getBySPMobileNumberAndStatus(mobileNumer, jobStatus);
	}
	
	public List<Job> getJobsByPincodeAndStatus(JobStatus jobStatus, String pincode) {
		return (List<Job>) jobService.getByPincodeAndStatus(jobStatus, pincode);
	}
	
	@Transactional
	public Job closeJob(long jobId) {
		return jobService.closeJob(jobService.getJobById(jobId));
	}
	
	@Transactional
	public Job assignJob(long jobId, ServiceProvider serviceProvider) throws IllegalArgumentException, NullPointerException {
		Job job = jobService.getJobById(jobId);
		logger.info("Service provider service "+job.getId());
		ServiceProvider sp = getByMobileNumber(serviceProvider.getMobileNumber());
		if (sp == null) throw new NullPointerException("Service provider with the given mobile number does not exist");
		logger.info(sp.getMobileNumber());
		logger.info(sp.getJobTypes());
		logger.info(job.getJobType());
		logger.info("sp.getJobTypes().contains(job.getJobType()) "+sp.getJobTypes().contains(job.getJobType()));
		if (!sp.getJobTypes().contains(job.getJobType())) throw new IllegalArgumentException("Service provider is not registerd to take up this job type");
		if (!sp.getPincodesServiced().contains(job.getPincode())) throw new IllegalArgumentException("Service provider cannot service this job area");
		//Job j = new Job(job.getJobType(), job.getJobStatus(), job.getCustomerName(), job.getPincode(), job.getPincode(), long customerMobileNumber, long serviceProviderMobileNumber, String serviceProviderName, Date dateInitiated)
		job.setJobStatus(JobStatus.ASSIGNED);
		job.setServiceProviderName(sp.getName());
		job.setServiceProviderMobileNumber(sp.getMobileNumber());
		return jobService.assignBySP(job);
	}
	
	public List<Job> getJobsMatchingProfile(long mobileNumber) {
		ServiceProvider sp = getByMobileNumber(mobileNumber);
		if (sp == null) throw new NullPointerException("Service provider with the mobile number not found");
		logger.info("Service provider available with mobile number "+mobileNumber);
		List<Job> matchingJobs = new ArrayList<Job>();
		List<Job> openJobs = null;
		List<Job> myAssignedJobs = null;
		for (JobType jobType: sp.getJobTypes()) {
			for (String pincode: sp.getPincodesServiced()) {
				openJobs = jobService.getByTypeStatusAndPincode(jobType, JobStatus.OPEN, pincode);
				matchingJobs.addAll(openJobs);
				openJobs = null;
			}
		}

		myAssignedJobs = getJobsByMobileNumberAndStatus(sp.getMobileNumber(), JobStatus.ASSIGNED);
		matchingJobs.addAll(myAssignedJobs);
		return matchingJobs;
	}

	public List<Job> getOpenAssignedAgreedJobsMatchingProfile(long mobileNumber) {
		ServiceProvider sp = getByMobileNumber(mobileNumber);
		if (sp == null) throw new NullPointerException("Service provider with the mobile number not found");
		logger.info("Service provider available with mobile number "+mobileNumber);
		List<Job> matchingJobs = new ArrayList<Job>();
		List<Job> openJobs = null;
		List<Job> myAssignedJobs = null;
		List<Job> myAgreedJobs = null;
		for (JobType jobType: sp.getJobTypes()) {
			for (String pincode: sp.getPincodesServiced()) {
				openJobs = jobService.getByTypeStatusAndPincode(jobType, JobStatus.OPEN, pincode);
				matchingJobs.addAll(openJobs);
				openJobs = null;
			}
		}

		myAssignedJobs = getJobsByMobileNumberAndStatus(sp.getMobileNumber(), JobStatus.ASSIGNED);
		matchingJobs.addAll(myAssignedJobs);
		myAgreedJobs = getJobsByMobileNumberAndStatus(sp.getMobileNumber(), JobStatus.AGREED);
		matchingJobs.addAll(myAgreedJobs);
		return matchingJobs;
	}


}
