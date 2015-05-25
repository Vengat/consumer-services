package com.bang.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.bang.model.Job;

public interface JobRepository extends CrudRepository<Job, Long> {

    //List<Job> findJobByCustomerNameAndPincode(String customerName, String pincode);
    
    //Job save(Job persisted);
    
    //Job findOne(long id);
}