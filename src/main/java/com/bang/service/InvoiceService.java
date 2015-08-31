package com.bang.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bang.controller.exception.BadCouponException;
import com.bang.controller.exception.CustomerNotFoundException;
import com.bang.controller.exception.ServiceProviderNotFoundException;
import com.bang.dao.CouponRepository;
import com.bang.dao.InvoiceRepository;
import com.bang.misc.DiscountCalculator;
import com.bang.model.Coupon;
import com.bang.model.Invoice;

@Service
@Component
public class InvoiceService {

	private static final Logger logger = Logger.getLogger(InvoiceService.class);
	
	@Autowired
	InvoiceRepository repository;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ServiceProviderService serviceProviderService;
	
	@Autowired
	CouponService couponService;
	/**
	 * 
	 * @param invoice
	 * @return
	 * @throws BadCouponException 
	 */
	@Transactional
	public Invoice create(Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, BadCouponException {
		logger.info(invoice.getCustomerMobileNumber());
		if (!customerService.isCustomerExists(invoice.getCustomerMobileNumber())) throw new CustomerNotFoundException("Customer with the mobile number could not be found");
		if (!serviceProviderService.isServiceProvider(invoice.getServiceProviderMobileNumber())) throw new ServiceProviderNotFoundException("Service provider with the mobile number could not be found");
		if (invoice.getLabourCharges().compareTo(BigDecimal.ZERO) < 0) {
			invoice.setLabourCharges(new BigDecimal("0"));
		}
		if (invoice.getMaterialCharges().compareTo(BigDecimal.ZERO) < 0) {
			invoice.setMaterialCharges(new BigDecimal("0"));
		}
		invoice.setTotalCharges(invoice.getMaterialCharges().add(invoice.getLabourCharges()));
		if (invoice.getCouponCode().trim().isEmpty()) return repository.save(invoice);
		Invoice inv = applyCoupon(invoice);
		return repository.save(inv);
	}
	
	@Transactional
	public Invoice display(Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, BadCouponException {
		logger.info(invoice.getCustomerMobileNumber());
		if (!customerService.isCustomerExists(invoice.getCustomerMobileNumber())) throw new CustomerNotFoundException("Customer with the mobile number could not be found");
		if (!serviceProviderService.isServiceProvider(invoice.getServiceProviderMobileNumber())) throw new ServiceProviderNotFoundException("Service provider with the mobile number could not be found");
		if (invoice.getLabourCharges().compareTo(BigDecimal.ZERO) < 0) {
			invoice.setLabourCharges(new BigDecimal("0"));
		}
		if (invoice.getMaterialCharges().compareTo(BigDecimal.ZERO) < 0) {
			invoice.setMaterialCharges(new BigDecimal("0"));
		}
		invoice.setTotalCharges(invoice.getMaterialCharges().add(invoice.getLabourCharges()));
		if (invoice.getCouponCode().trim().isEmpty()) return invoice;
		Invoice inv = applyCoupon(invoice);
		return inv;
	}
	
	/**
	 * 
	 * @param invoice
	 * @return
	 */
	@Transactional
	public Invoice updateCharges(Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, NullPointerException, BadCouponException {
		if (!customerService.isCustomerExists(invoice.getCustomerMobileNumber())) throw new CustomerNotFoundException("Customer with the mobile number could not be found");
		if (!serviceProviderService.isServiceProvider(invoice.getServiceProviderMobileNumber())) throw new ServiceProviderNotFoundException("Service provider with the mobile number could not be found");
		Invoice inv = repository.findOne(invoice.getId());
		if (inv == null)throw new NullPointerException("Invoice not found");
		if (!inv.getLabourCharges().equals(invoice.getLabourCharges()) && inv.getLabourCharges().compareTo(BigDecimal.ZERO) >= 0) {
			inv.setLabourCharges(invoice.getLabourCharges());
		}
		if (!inv.getMaterialCharges().equals(invoice.getMaterialCharges()) && inv.getMaterialCharges().compareTo(BigDecimal.ZERO) >= 0) {
			inv.setMaterialCharges(invoice.getMaterialCharges());
		}
		invoice.setTotalCharges(invoice.getMaterialCharges().add(invoice.getLabourCharges()));

		String couponCode = inv.getCouponCode();
		if (couponCode != null && !couponCode.trim().isEmpty()) {
			Coupon coupon = couponService.getCouponByCode(couponCode);
			if (coupon == null) throw new NullPointerException("Coupon not found");
			if (couponService.isCouponExpired(coupon)) throw new BadCouponException("Expired coupon");
			inv = applyDiscount(inv, coupon);
		}
		return repository.save(inv);
	}
	
	private Invoice applyCoupon(Invoice inv) throws BadCouponException, NullPointerException {
		String couponCode = inv.getCouponCode();
		logger.info("Coupon code is "+couponCode);
		if (couponCode.trim().isEmpty()) return inv;	
		Coupon coupon = couponService.getCouponByCode(couponCode);
		if (coupon == null) throw new NullPointerException("Coupon not found");
		if (couponService.isCouponExpired(coupon)) throw new BadCouponException("Expired coupon");
		inv = applyDiscount(inv, coupon);
		return inv;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws BadCouponException 
	 * @throws NullPointerException 
	 * @throws ServiceProviderNotFoundException 
	 * @throws CustomerNotFoundException 
	 */
	public Invoice updatelabourChargesById(long id) throws CustomerNotFoundException, ServiceProviderNotFoundException, NullPointerException, BadCouponException {
		return updateLabourCharges(getById(id));
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws BadCouponException 
	 * @throws NullPointerException 
	 * @throws ServiceProviderNotFoundException 
	 * @throws CustomerNotFoundException 
	 */
	public Invoice updatematerialChargesById(long id) throws CustomerNotFoundException, ServiceProviderNotFoundException, NullPointerException, BadCouponException {
		return updateMaterialCharges(getById(id));
	}
	
	/**
	 * 
	 * @param invoice
	 * @return
	 * @throws BadCouponException 
	 */
	@Transactional
	public Invoice updateLabourCharges(Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, NullPointerException, BadCouponException {
		Invoice inv = repository.findOne(invoice.getId());
		if (!customerService.isCustomerExists(invoice.getCustomerMobileNumber())) throw new CustomerNotFoundException("Customer with the mobile number could not be found");
		if (!serviceProviderService.isServiceProvider(invoice.getServiceProviderMobileNumber())) throw new ServiceProviderNotFoundException("Service provider with the mobile number could not be found");
		if (inv == null)throw new NullPointerException("Invoice not found");
		if (!inv.getLabourCharges().equals(invoice.getLabourCharges()) && invoice.getLabourCharges().compareTo(BigDecimal.ZERO) >=0) {
			inv.setLabourCharges(invoice.getLabourCharges());
			inv.setTotalCharges(inv.getLabourCharges().add(inv.getMaterialCharges()));
		}
		inv = applyCoupon(inv);
		return repository.save(inv);
	}
	
	/**
	 * 
	 * @param invoice
	 * @return
	 * @throws BadCouponException 
	 */
	@Transactional
	public Invoice updateMaterialCharges(Invoice invoice) throws CustomerNotFoundException, ServiceProviderNotFoundException, NullPointerException, BadCouponException {
		Invoice inv = repository.findOne(invoice.getId());
		if (!customerService.isCustomerExists(invoice.getCustomerMobileNumber())) throw new CustomerNotFoundException("Customer with the mobile number could not be found");
		if (!serviceProviderService.isServiceProvider(invoice.getServiceProviderMobileNumber())) throw new ServiceProviderNotFoundException("Service provider with the mobile number could not be found");
		if (inv == null)throw new NullPointerException("Invoice not found");
		if (!inv.getMaterialCharges().equals(invoice.getMaterialCharges()) && invoice.getMaterialCharges().compareTo(BigDecimal.ZERO) >=0) {
			inv.setMaterialCharges(invoice.getMaterialCharges());
			inv.setTotalCharges(inv.getLabourCharges().add(inv.getMaterialCharges()));
		}
		inv = applyCoupon(inv);
		return repository.save(inv);
	}
	
	/*
	 * DO NOT EVER ENABLE THESE METHODS
	 */
	
	/*@Transactional
	public Invoice updateCustomerMobileNumber(Invoice invoice) {
		Invoice inv = repository.findOne(invoice.getId());
		if (inv == null)throw new NullPointerException("Invoice not found");
		if (inv.getCustomerMobileNumber()!= invoice.getCustomerMobileNumber()) {
			inv.setCustomerMobileNumber(invoice.getCustomerMobileNumber());
		}
		return inv;
	}
	
	@Transactional
	public Invoice updateServiceProviderMobileNumber(Invoice invoice) {
		Invoice inv = repository.findOne(invoice.getId());
		if (inv == null)throw new NullPointerException("Invoice not found");
		if (inv.getServiceProviderMobileNumber()!= invoice.getServiceProviderMobileNumber()) {
			inv.setServiceProviderMobileNumber(invoice.getServiceProviderMobileNumber());
		}
		return inv;
	}
	*/
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Invoice getById(long id)throws NullPointerException {
		Invoice inv= repository.findInvoiceById(id);
		if (inv == null) throw new NullPointerException("Invoice does not exist");
		return inv;
	}
	
	/**
	 * 
	 * @param id
	 * @param customerMobileNumber
	 * @return
	 */
	public Invoice getByIdAndCustomerMobileNumber(long id, long customerMobileNumber)throws NullPointerException {
		Invoice inv = repository.findInvoiceByIdAndCustomerMobileNumber(id, customerMobileNumber);
		if (inv == null) throw new NullPointerException("Invoice does not exist");
		return inv;
	}
	
	/**
	 * 
	 * @param id
	 * @param serviceProviderMobileNumber
	 * @return
	 */
	public Invoice getByIdAndServiceProviderMobileNumber(long id, long serviceProviderMobileNumber)throws NullPointerException {
		Invoice inv = repository.findInvoiceByIdAndServiceProviderMobileNumber(id, serviceProviderMobileNumber);
		if (inv == null) throw new NullPointerException("Invoice does not exist");
		return inv;
	}
	
	/**
	 * 
	 * @param invoiceDate
	 * @param id
	 * @return
	 */
	public Invoice getByInvoiceDateAndId(Date invoiceDate, long id)throws NullPointerException {
		Invoice inv = repository.findByInvoiceDateAndId(invoiceDate, id);
		if (inv == null) throw new NullPointerException("Invoice does not exist");
		return inv;
	}
	
	/**
	 * 
	 * @param customerMobileNumber
	 * @return
	 */
	public List<Invoice> getInvoicesByCustomerMobileNumber(long customerMobileNumber) {
		List<Invoice> invs = repository.findInvoicesByCustomerMobileNumber(customerMobileNumber);
		return invs;	
	}
	
	/**
	 * This and the next methods are redundant, anyway :P
	 * @param date
	 * @param customerMobileNumber
	 * @return
	 */
	public List<Invoice> getInvoicesByDateAndCustomerMobileNumber(Date date, long customerMobileNumber) {
		List<Invoice> invs = repository.findByInvoiceDateAndCustomerMobileNumber(date, customerMobileNumber);
		return invs;
	}
	
	/**
	 * 
	 * @param date
	 * @param customerMobileNumber
	 * @return
	 */
	public List<Invoice> getInvoicesByDateAndServiceProviderMobileNumber(Date date, long customerMobileNumber) {
		List<Invoice> invs = repository.findByInvoiceDateAndServiceProviderMobileNumber(date, customerMobileNumber);
		return invs;
	}
	
	public Invoice applyDiscount(Invoice invoice, Coupon coupon) {
		
		Invoice inv;
		
		switch(coupon.getCouponType()) {
		
		case DUMMY:
			inv = invoice;
			break;
		case ABSOLUTE_DISCOUNT_PERCENT_ON_TOTAL:
			inv = DiscountCalculator.applyAbsoluteDiscPercentTotal(invoice, coupon);
			break;
		case ABSOLUTE_DISCOUNT_PERCENT_ON_LABOUR:
			inv = DiscountCalculator.applyAbsoluteDiscPercentLabour(invoice, coupon);
			break;
		case ABSOLUTE_DISCOUNT_PERCENT_ON_MATERIAL:
			inv = DiscountCalculator.applyAbsoluteDiscPercentMaterial(invoice, coupon);
			break;
		case ABSOLUTE_CASH_BACK_ON_TOTAL:
			inv = invoice;
			break;
		case ABSOLUTE_CASH_BACK_ON_LABOUR:
			inv = invoice;
			break;
		case ABSOLUTE_CASH_BACK_ON_MATERIAL:
			inv = invoice;
			break;
		case DISCOUNT_PERCENT_ON_TOTAL_WITH_MAX_AMOUNT:
			inv = DiscountCalculator.applyDiscountPercentOnTotalWithMaxDiscountAmount(invoice, coupon);
			break;
		case DISCOUNT_PERCENT_ON_LABOUR_WITH_MAX_AMOUNT:
			inv = DiscountCalculator.applyDiscountPercentOnLaborWithMaxDiscountAmount(invoice, coupon);
			break;
		case DISCOUNT_PERCENT_ON_MATERIAL_WITH_MAX_AMOUNT:
			inv = DiscountCalculator.applyDiscountPercentOnMaterialWithMaxDiscountAmount(invoice, coupon);
			break;
		case ABSOLUTE_DISCOUNT_CASH_ON_TOTAL:
			inv = DiscountCalculator.applyAbsoluteCashDiscountOnTotal(invoice, coupon);
			break;
		case ABSOLUTE_DISCOUNT_CASH_ON_LABOUR:
			inv = DiscountCalculator.applyAbsoluteCashDiscountOnLabor(invoice, coupon);
			break;
		case ABSOLUTE_DISCOUNT_CASH_ON_MATERIAL:
			inv = DiscountCalculator.applyAbsoluteCashDiscountOnMaterial(invoice, coupon);
			break;
		/*case CASH_BACK_ON_TOTAL_WITH_MAX_AMOUNT:
			
			break;
		case CASH_BACK_ON_LABOUR_WITH_MAX_AMOUNT:
			
			break;
		case CASH_BACK_ON_MATERIAL_WITH_MAX_AMOUNT:
			
			break;*/
		default:
			logger.info("Coupon type not found to validate");
			inv = invoice;
			break;		
		}
		
		return inv;
	}

	
}
