package com.bang.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bang.misc.JobStatus;
import com.bang.misc.JobType;
import com.bang.model.Job;

public interface JobRepository extends CrudRepository<Job, Long> {

    List<Job> findJobsByCustomerNameAndPincode(String customerName, String pincode);
    
    List<Job> findJobsByPincode(String pincode);
    
    List<Job> findJobsByJobStatus(String jobStatus);
    
    List<Job> findJobsByJobStatusAndPincode(JobStatus jobStatus, String pincode);
    
    List<Job> findJobsByJobTypeAndJobStatusAndPincode(JobType jobType, JobStatus jobStatus, String pincode);
    
    //Job save(Job persisted);
    
    //Job findOne(long id);
}