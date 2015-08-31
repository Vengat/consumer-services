package com.bang.controller;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bang.controller.exception.BadCouponException;
import com.bang.controller.exception.CustomerNotFoundException;
import com.bang.controller.exception.ServiceProviderNotFoundException;
import com.bang.model.Invoice;
import com.bang.service.InvoiceService;
import com.bang.service.JobService;

/**
 * @author Vengat
 *
 */
@RestController
public class InvoiceController {
	
	private static final Logger logger = Logger.getLogger(InvoiceController.class);
	
	@Autowired
	InvoiceService service;
	
	@RequestMapping(value = "/invoices",  method = RequestMethod.GET)
	public ResponseEntity<Invoice> get() {
		logger.info("At invoice controller");
		Invoice invoice = new Invoice(0L, 0L, "", 0L, "", new BigDecimal("0.0"), new BigDecimal("0.0"));
		invoice.setTotalCharges(new BigDecimal("0.0"));
		invoice.setDiscountedLabourCharges(new BigDecimal("0.0"));
		invoice.setDiscountedMaterialCharges(new BigDecimal("0.0"));
		invoice.setDiscountedTotalCharges(new BigDecimal("0.0"));
		return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/invoices", method = RequestMethod.POST)
	public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, BadCouponException {
		invoice.setTotalCharges(new BigDecimal("0.0"));
		invoice.setDiscountedLabourCharges(new BigDecimal("0.0"));
		invoice.setDiscountedMaterialCharges(new BigDecimal("0.0"));
		invoice.setDiscountedTotalCharges(new BigDecimal("0.0"));
		Invoice inv = service.create(invoice);
		return new ResponseEntity<Invoice>(inv, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/invoices/display", method = RequestMethod.POST)
	public ResponseEntity<Invoice> calculateInvoiceAmountToDisplay(@RequestBody Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, BadCouponException {
		invoice.setTotalCharges(new BigDecimal("0.0"));
		invoice.setDiscountedLabourCharges(new BigDecimal("0.0"));
		invoice.setDiscountedMaterialCharges(new BigDecimal("0.0"));
		invoice.setDiscountedTotalCharges(new BigDecimal("0.0"));
		Invoice inv = service.display(invoice);
		return new ResponseEntity<Invoice>(inv, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/invoices/updateCharges")
	public ResponseEntity<Invoice> updateCharges(@RequestBody Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, NullPointerException, BadCouponException {
		invoice.setTotalCharges(new BigDecimal("0.0"));
		invoice.setDiscountedLabourCharges(new BigDecimal("0.0"));
		invoice.setDiscountedMaterialCharges(new BigDecimal("0.0"));
		invoice.setDiscountedTotalCharges(new BigDecimal("0.0"));
		Invoice inv = service.updateCharges(invoice);
		return new ResponseEntity<Invoice>(inv, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/invoices/updateLabourCharges")
	public ResponseEntity<Invoice> updateLabourCharges(@RequestBody Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, NullPointerException, BadCouponException {
		invoice.setTotalCharges(new BigDecimal("0.0"));
		invoice.setDiscountedLabourCharges(new BigDecimal("0.0"));
		invoice.setDiscountedMaterialCharges(new BigDecimal("0.0"));
		invoice.setDiscountedTotalCharges(new BigDecimal("0.0"));
		Invoice inv = service.updateLabourCharges(invoice);
		return new ResponseEntity<Invoice>(inv, HttpStatus.OK);
	}

	@RequestMapping(value = "/invoices/updateMaterialCharges")
	public ResponseEntity<Invoice> updateMaterialCharges(@RequestBody Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, NullPointerException, BadCouponException {
		invoice.setTotalCharges(new BigDecimal("0.0"));
		invoice.setDiscountedLabourCharges(new BigDecimal("0.0"));
		invoice.setDiscountedMaterialCharges(new BigDecimal("0.0"));
		invoice.setDiscountedTotalCharges(new BigDecimal("0.0"));
		Invoice inv = service.updateMaterialCharges(invoice);
		return new ResponseEntity<Invoice>(inv, HttpStatus.OK);
	}
}
