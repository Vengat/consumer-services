/**
 * 
 */
package com.bang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bang.dao.CustomerRepository;
import com.bang.model.Customer;

/**
 * @author vengat.r
 *
 */
@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository repository;
	
	public Customer create(Customer customer) {
		if (getByMobileNumber(customer.getMobileNumber()) != null) return getByMobileNumber(customer.getMobileNumber());
		return repository.save(customer);
	}
	
	public Customer getByMobileNumber(long mobileNumber) {
		return repository.findByMobileNumber(mobileNumber);
	}
	

}
