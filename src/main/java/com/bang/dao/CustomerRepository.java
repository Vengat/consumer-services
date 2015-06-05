package com.bang.dao;

import org.springframework.data.repository.CrudRepository;

import com.bang.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    public Customer findByMobileNumber(long mobileNumber);
}
