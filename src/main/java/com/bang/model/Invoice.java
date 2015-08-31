package com.bang.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1149987956134162218L;
	
	@Id
	@Column(name = "INVOICE_ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "customer_name", nullable = false, length = 50)
	private String customerName;
	
	@Column(name = "customer_mobile_number", nullable = false)
	private long customerMobileNumber;
	
	@Column(name = "service_provider_name", length = 50, nullable = false)
	private String serviceProviderName;
	

	@Column(name = "sp_mobile_number", nullable = false)
	private long serviceProviderMobileNumber;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "customer_mobile_number", referencedColumnName = "mobile_number",  insertable = false, updatable = false)
	private Customer customer;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "sp_mobile_number", referencedColumnName = "mobile_number",  insertable = false, updatable = false)
	private ServiceProvider serviceProvider;
	
	//@OneToOne(mappedBy = "invoice")
	@OneToOne(optional = false, mappedBy = "invoice")	
	//@JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID",  insertable = false, updatable = false)
	private Job job;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)	
	@JoinColumn(name = "fk_coupon_code", referencedColumnName = "coupon_code",  insertable = false, updatable = false)
	private Coupon coupon;
	
	@Column(name = "coupon_code")
	private String couponCode;
	
	@Column(name = "labour_charges")
	private BigDecimal labourCharges;
	
	@Column(name = "material_charges")
	private BigDecimal materialCharges;
	
	@Column(name = "total_charges")
	private BigDecimal totalCharges;
	
	@Column(name = "discounted_labour_charges")
	private BigDecimal discountedLabourCharges;
	
	@Column(name = "discounted_material_charges")
	private BigDecimal discountedMaterialCharges;
	
	@Column(name = "discounted_total_charges")
	private BigDecimal discountedTotalCharges;
	
	@Column(name = "INV_JOB_ID", nullable = false)
	private long jobId;
	
	@Column(name = "invoice_date")
	private Date invoiceDate;

	private static int ROUNDING_MODE = BigDecimal.ROUND_CEILING;
	
	protected Invoice() {
		
	}
	
	public Invoice(long jobId, long customerMobileNumber, String customerName, long serviceProviderMobileNumber, String serviceProviderName, BigDecimal labourCharges, BigDecimal materialCharges) {
		this.jobId = jobId;
		this.customerMobileNumber = customerMobileNumber;
		this.customerName = customerName;
		this.serviceProviderMobileNumber = serviceProviderMobileNumber;
		this.serviceProviderName = serviceProviderName;
		this.labourCharges = labourCharges;
		this.materialCharges = materialCharges;
		this.invoiceDate = new Date();
	}
	
	public BigDecimal getLabourCharges() {
		return labourCharges;
	}

	public void setLabourCharges(BigDecimal labourCharges) throws IllegalArgumentException {
		if (labourCharges.compareTo(BigDecimal.ZERO) < 0 && labourCharges != null) throw new IllegalArgumentException("Charges should be non-negative labour charges");;
		this.labourCharges = labourCharges;
		this.labourCharges = rounded(this.labourCharges);
	}

	public BigDecimal getMaterialCharges() {
		return materialCharges;
	}

	public void setMaterialCharges(BigDecimal materialCharges) throws IllegalArgumentException {
		if (materialCharges.compareTo(BigDecimal.ZERO) < 0 && materialCharges != null) throw new IllegalArgumentException("Charges should be non-negative material charges");
		this.materialCharges = materialCharges;
		this.materialCharges = rounded(this.materialCharges);
	}

	public BigDecimal getTotalCharges() {
		return totalCharges;
	}

	@Column(name = "total_charges")
	public void setTotalCharges(BigDecimal totalCharges) throws IllegalArgumentException {
		if (totalCharges.compareTo(BigDecimal.ZERO) < 0 && totalCharges != null) throw new IllegalArgumentException("Charges should be non-negative material charges");
		this.totalCharges = totalCharges;//getLabourCharges().add(getMaterialCharges());
		this.totalCharges = rounded(this.totalCharges);
	}
	
	public BigDecimal getDiscountedLabourCharges() {
		return discountedLabourCharges;
	}

	public void setDiscountedLabourCharges(BigDecimal discountedLabourCharges) {
		if (discountedLabourCharges.compareTo(BigDecimal.ZERO) < 0 && discountedLabourCharges != null) throw new IllegalArgumentException("Charges should be non-negative labour charges");
		this.discountedLabourCharges = rounded(discountedLabourCharges);
	}

	public BigDecimal getDiscountedMaterialCharges() {
		return discountedMaterialCharges;
	}

	public void setDiscountedMaterialCharges(BigDecimal discountedMaterialCharges) {
		if (discountedMaterialCharges.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Charges should be non-negative material charges");
		this.discountedMaterialCharges = rounded(discountedMaterialCharges);
	}

	public BigDecimal getDiscountedTotalCharges() {
		return discountedTotalCharges;
	}

	public void setDiscountedTotalCharges(BigDecimal discountedTotalCharges) {
		if (discountedTotalCharges.compareTo(BigDecimal.ZERO) < 0 && discountedTotalCharges != null) throw new IllegalArgumentException("Charges should be non-negative total charges");
		this.discountedTotalCharges = rounded(discountedTotalCharges);
	}
	
	public long getId() {
		return id;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public void setCustomerMobileNumber(long customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public long getServiceProviderMobileNumber() {
		return serviceProviderMobileNumber;
	}

	public void setServiceProviderMobileNumber(long serviceProviderMobileNumber) {
		this.serviceProviderMobileNumber = serviceProviderMobileNumber;
	}
	
	private BigDecimal rounded(BigDecimal amount){
	    return amount.setScale(2, ROUNDING_MODE);
    }
	 
	public long getJobId() {
	    return jobId;
	}

	public void setJobId(long jobId) {
	    this.jobId = jobId;
	}

	public Date getInvoiceDate() {
	    return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
	    this.invoiceDate = invoiceDate;
	}
	

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

}
