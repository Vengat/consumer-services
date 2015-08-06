/**
 * 
 */
package com.bang.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bang.misc.JobType;
import com.bang.model.ServiceProvider;

/**
 * @author vengat.r
 *
 */
public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {
	
	public ServiceProvider findByMobileNumber(long mobileNumber);
	
	@Query("from ServiceProvider sp where ?1 member of sp.pincodesServiced AND ?2 member of sp.jobTypes")
	//@Query("SELECT sp FROM ServiceProvider WHERE sp member of sp.pincodesServiced AND sp member of sp.jobTypes")
	//@Query("from ServiceProvider sp where ?1 member of sp.jobTypes")
	public List<ServiceProvider> findByPincodesServicedAndJobType(String pincode, String jobType);

}