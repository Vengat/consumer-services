/**
 * 
 */
package com.bang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bang.dao.ServiceProviderRepository;
import com.bang.model.ServiceProvider;

/**
 * @author vengat.r
 *
 */
@Service
public class ServiceProviderService {
	
	@Autowired
	ServiceProviderRepository repository;
	
	public ServiceProvider create(ServiceProvider serviceProvider) {
		return repository.save(serviceProvider);
	}
	
	public ServiceProvider getById(long id) {
		return repository.findOne(id);
	}
	
	public ServiceProvider update(ServiceProvider serviceProvider) {
		ServiceProvider sp = getById(serviceProvider.getId());
		if (!sp.getName().equals(serviceProvider.getName())) sp.setName(serviceProvider.getName());
		if (sp.getMobileNumber() != serviceProvider.getMobileNumber()) sp.setMobileNumber(serviceProvider.getMobileNumber());
		if (serviceProvider.getJobTypes() != null && !sp.getJobTypes().equals(serviceProvider.getJobTypes())) sp.setJobTypes(serviceProvider.getJobTypes());
		return repository.save(sp);
	}
	
	public List<ServiceProvider> getAll() {
		return (List<ServiceProvider>) repository.findAll();	
	}

}
