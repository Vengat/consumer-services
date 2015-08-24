package com.bang.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;

import com.bang.misc.CouponType;

@Entity
@Table(name = "coupons")
public class Coupon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2504454741530322776L;
	
	private static int ROUNDING_MODE = BigDecimal.ROUND_CEILING;
	
	@Id
	@Column(name = "COUPON_ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "coupon_code", nullable = false, unique = true)
	private String couponCode;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "start_date", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startDate;
	
	@Column(name = "validity_period_days", nullable = false)
	//@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private int validityPeriodDays;
	
	@Enumerated(EnumType.STRING)
	private CouponType couponType;
	
	@Column(name = "is_discount_labor_charges", nullable = false)
	private boolean isDiscountOnLaborCharges;
	
	@Column(name = "is_discount_material_charges", nullable = false)
	private boolean isDiscountOnMaterialCharges;
	
	@Column(name = "is_discount_total_charges", nullable = false)
	private boolean isDiscountOnTotalCharges;
	
	@Column(name = "is_discount_percent", nullable = false)
	private boolean isDicountInPercent;
	
	@Column(name = "is_discount_cash", nullable = false)
	private boolean isDiscountInCash;
	
	@Column(name = "discount_percent")
	private int discountPercentage;
	
	@Column(name = "max_discount_percent")
	private int maxDiscountPercentage;
	
	@Column(name = "discount_amount")
	private BigDecimal discountAmount;
	
	@Column(name = "maximum_discount_amount")
	private BigDecimal maximumDiscountAmount;
	
	@Column(name = "minimum_purchase_amount")
	private BigDecimal minimumPurchaseAmount;
	
	@ElementCollection(targetClass = String.class)
	@CollectionTable(name = "coupon_applicable_cities", joinColumns=@JoinColumn(name="COUPON_ID"))
	@Column(name = "applicable_cities")
	private List<String> applicableCities;
	

	
	public Coupon(String couponCode, String description, 
			DateTime startDate, int validityPeriodDays, 
			CouponType couponType, boolean isDiscountOnLaborCharges, 
			boolean isDiscountOnMaterialCharges, boolean isDiscountOnTotalCharges, 
			boolean isDicountInPercent, boolean isDiscountInCash, 
			int discountPercentage, int maxDiscountPercentage, 
			BigDecimal discountAmount, BigDecimal maximumDiscountAmount, 
			BigDecimal minimumPurchaseAmount,
			List<String> applicableCities) {
		
		this.couponCode = couponCode;
		this.description = description;
		this.startDate = startDate;
		this.validityPeriodDays = validityPeriodDays;
		this.couponType = couponType;
		this.isDiscountOnLaborCharges = isDiscountOnLaborCharges;
		this.isDiscountOnMaterialCharges = isDiscountOnMaterialCharges;
		this.isDiscountOnTotalCharges = isDiscountOnTotalCharges;
		this.isDicountInPercent = isDicountInPercent;
		this.isDiscountInCash = isDiscountInCash;
		this.discountPercentage = discountPercentage;
		this.maxDiscountPercentage = maxDiscountPercentage;
		this.discountAmount = discountAmount;
		this.maximumDiscountAmount = maximumDiscountAmount;
		this.minimumPurchaseAmount = minimumPurchaseAmount;
		this.applicableCities = applicableCities;		
	}
	
	protected Coupon() {
		
	}
	
	/**
	 * <p>
	 * Validate that the start date plus the valid days is after the current instance
	 * </p>
	 * @return boolean
	 */
	public boolean isCouponValid() {
		return this.startDate.plusDays(getValidityPeriodDays()).isAfterNow();
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public int getValidityPeriodDays() {
		return validityPeriodDays;
	}

	public CouponType getCouponType() {
		return couponType;
	}

	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}

	public boolean isDiscountOnLaborCharges() {
		return isDiscountOnLaborCharges;
	}

	public void setDiscountOnLaborCharges(boolean isDiscountOnLaborCharges) {
		this.isDiscountOnLaborCharges = isDiscountOnLaborCharges;
	}

	public boolean isDiscountOnMaterialCharges() {
		return isDiscountOnMaterialCharges;
	}

	public void setDiscountOnMaterialCharges(boolean isDiscountOnMaterialCharges) {
		this.isDiscountOnMaterialCharges = isDiscountOnMaterialCharges;
	}

	public boolean isDiscountOnTotalCharges() {
		return isDiscountOnTotalCharges;
	}

	public void setDiscountOnTotalCharges(boolean isDiscountOnTotalCharges) {
		this.isDiscountOnTotalCharges = isDiscountOnTotalCharges;
	}

	public boolean isDicountInPercent() {
		return isDicountInPercent;
	}

	public void setDicountInPercent(boolean isDicountInPercent) {
		this.isDicountInPercent = isDicountInPercent;
	}

	public boolean isDiscountInCash() {
		return isDiscountInCash;
	}

	public void setDiscountInCash(boolean isDiscountInCash) {
		this.isDiscountInCash = isDiscountInCash;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public int getMaxDiscountPercentage() {
		return maxDiscountPercentage;
	}

	public void setMaxDiscountPercentage(int maxDiscountPercentage) {
		this.maxDiscountPercentage = maxDiscountPercentage;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		if (discountAmount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Discount amount should be non-negative");
		this.discountAmount = discountAmount;
		this.discountAmount = rounded(discountAmount);
	}

	public BigDecimal getMaximumDiscountAmount() {
		return maximumDiscountAmount;
	}

	public void setMaximumDiscountAmount(BigDecimal maximumDiscountAmount) {
		if (discountAmount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Maximum discount amount should be non-negative");
		this.maximumDiscountAmount = maximumDiscountAmount;
		this.maximumDiscountAmount = rounded(maximumDiscountAmount);
	}

	
	public static int getROUNDING_MODE() {
		return ROUNDING_MODE;
	}

	public static void setROUNDING_MODE(int rOUNDING_MODE) {
		ROUNDING_MODE = rOUNDING_MODE;
	}

	public BigDecimal getMinimumPurchaseAmount() {
		return minimumPurchaseAmount;
	}

	public void setMinimumPurchaseAmount(BigDecimal minimumPurchaseAmount) {
		if (minimumPurchaseAmount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Minimum purchase amount should be non-negative");
		this.minimumPurchaseAmount = minimumPurchaseAmount;
		this.minimumPurchaseAmount = rounded(minimumPurchaseAmount);
	}

	public void setValidityPeriodDays(int validityPeriodDays) {
		this.validityPeriodDays = validityPeriodDays;
	}

	public List<String> getApplicableCities() {
		return applicableCities;
	}

	public void setApplicableCities(List<String> applicableCities) {
		this.applicableCities = applicableCities;
	}

	public long getId() {
		return id;
	}
	
	private BigDecimal rounded(BigDecimal amount){
	    return amount.setScale(2, ROUNDING_MODE);
    }
}
