package com.bang.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bang.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	Invoice findInvoiceById(long Id);
	
	Invoice findInvoiceByIdAndCustomerMobileNumber(long id, long customerMobileNumber);
	
	List<Invoice> findInvoicesByCustomerMobileNumber(long customerMobileNumber);
	
	Invoice findInvoiceByIdAndServiceProviderMobileNumber(long id, long serviceProviderMobileNumber);
	
	List<Invoice> findInvoicesByServiceProviderMobileNumber(long serviceProviderMobileNumber);
	
	Invoice findByInvoiceDateAndId(Date date, long id);
	
	List<Invoice> findByInvoiceDateAndCustomerMobileNumber(Date date, long customerMobileNumber);
	
	List<Invoice> findByInvoiceDateAndServiceProviderMobileNumber(Date date, long customerMobileNumber);
}
