package com.bang.service;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bang.controller.exception.BadCouponException;
import com.bang.controller.exception.CouponExistsException;
import com.bang.dao.CouponRepository;
import com.bang.dao.JobRepository;
import com.bang.misc.CouponType;
import com.bang.model.Coupon;

@Service
@Component
public class CouponService {
	
private static final Logger logger = Logger.getLogger(CouponService.class);
	
	@Autowired
	CouponRepository repository;
	
	public Coupon create(Coupon coupon) throws BadCouponException, CouponExistsException {
		if (repository.findByCouponCode(coupon.getCouponCode()) != null) throw new CouponExistsException("Coupon exists");
		if (!validateCoupon(coupon)) throw new BadCouponException("Either the coupon type do not adhere to rules or does not exist");
		return repository.save(coupon);		
	}
	
	public Coupon getCouponByCode(String couponCode) throws BadCouponException {
		Coupon coupon = repository.findByCouponCode(couponCode);
		if (coupon == null) throw new NullPointerException("Coupon does not exist for the specified code");
		if (!coupon.isCouponValid()) throw new BadCouponException("Expired coupon");
		return coupon;
	}
	
	@Transactional
	public Coupon expireCouponByCode(String couponCode) throws BadCouponException {
		Coupon coupon = repository.findByCouponCode(couponCode);
		if (coupon == null) throw new NullPointerException("Coupon does not exist for the specified code");
		if (!coupon.isCouponValid()) throw new BadCouponException("Expired coupon");
        coupon.setValidityPeriodDays(0);
        return repository.save(coupon);
	}
	
	public boolean verifyCouponValidity(Coupon coupon) {
		return validateCoupon(coupon);
	}
	
	public boolean isCouponExpired(Coupon coupon) {
		return !coupon.isCouponValid();
	}
	
	public boolean validateCoupon(Coupon coupon) {
		
		boolean validCoupon = false;
		CouponType couponType = coupon.getCouponType();
		switch(couponType) {
		
		case DUMMY:
			validCoupon = false; //verifyDummyCoupon(coupon);
			break;
		case ABSOLUTE_DISCOUNT_PERCENT_ON_TOTAL:
			logger.info("**************ABSOLUTE_DISCOUNT_PERCENT_ON_TOTAL*********");
			validCoupon = verifyAbsoluteDiscPercentTotal(coupon);
			break;
		case ABSOLUTE_DISCOUNT_PERCENT_ON_LABOUR:
			validCoupon = verifyAbsoluteDiscPercentLabour(coupon);
			break;
		case ABSOLUTE_DISCOUNT_PERCENT_ON_MATERIAL:
			validCoupon = verifyAbsoluteDiscPercentMaterial(coupon);
			break;
		case ABSOLUTE_CASH_BACK_ON_TOTAL:
			validCoupon = false;
			break;
		case ABSOLUTE_CASH_BACK_ON_LABOUR:
			validCoupon = false;
			break;
		case ABSOLUTE_CASH_BACK_ON_MATERIAL:
			validCoupon = false;
			break;
		case DISCOUNT_PERCENT_ON_TOTAL_WITH_MAX_AMOUNT:
			validCoupon = verifyDiscountPercentOnTotalWthMaxDiscountAmount(coupon);
			break;
		case DISCOUNT_PERCENT_ON_LABOUR_WITH_MAX_AMOUNT:
			validCoupon = verifyDiscountPercentOnLaborWthMaxDiscountAmount(coupon);
			break;
		case DISCOUNT_PERCENT_ON_MATERIAL_WITH_MAX_AMOUNT:
			validCoupon = verifyDiscountPercentOnMaterialWthMaxDiscountAmount(coupon);
			break;
		case ABSOLUTE_DISCOUNT_CASH_ON_TOTAL:
			validCoupon = verifyAbsoluteCashDiscountOnTotal(coupon);
			break;
		case ABSOLUTE_DISCOUNT_CASH_ON_LABOUR:
			validCoupon = verifyAbsoluteCashDiscountOnLabor(coupon);
			break;
		case ABSOLUTE_DISCOUNT_CASH_ON_MATERIAL:
			validCoupon = verifyAbsoluteCashDiscountOnMaterial(coupon);
			break;
		/*case CASH_BACK_ON_TOTAL_WITH_MAX_AMOUNT:
			
			break;
		case CASH_BACK_ON_LABOUR_WITH_MAX_AMOUNT:
			
			break;
		case CASH_BACK_ON_MATERIAL_WITH_MAX_AMOUNT:
			
			break;*/
		default:
			logger.info("Coupon type not found to validate");
			validCoupon = false;
			break;		
		}
		
		return validCoupon;
	}
	
	private boolean verifyCommonCouponAttributes(Coupon coupon) {
		if (coupon.getCouponCode() == null) return false;
		if (coupon.getApplicableCities().isEmpty()) return false;
		logger.info("isCouponExpired(coupon) "+isCouponExpired(coupon));
		if (isCouponExpired(coupon)) return false;
		if (coupon.getDescription().isEmpty()) return false;
		if (coupon.getMaxDiscountPercentage() < 0) return false;
		if (coupon.getMaximumDiscountAmount().compareTo(BigDecimal.ZERO) < 0) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) < 0) return false;
		if (coupon.getMinimumPurchaseAmount().compareTo(BigDecimal.ZERO) < 0) return false;
		if (coupon.getDiscountPercentage() < 0) return false;
		if (coupon.getStartDate().isBefore(new DateTime().getDayOfMonth())) return false;
		return true;
	}
	
	private boolean verifyPercentageDiscountCoupons(Coupon coupon) {
		if (!coupon.isDicountInPercent()) return false;
		if (coupon.isDiscountInCash()) return false;
		return true;
	}
	
	private boolean verifyCashDiscountCoupons(Coupon coupon) {
		if (coupon.isDicountInPercent()) return false;
		if (!coupon.isDiscountInCash()) return false;
		return true;
	}
	
	private boolean verifyDummyCoupon(Coupon coupon) {
		return true;		
	}
	
	private boolean verifyAbsoluteDiscPercentTotal(Coupon coupon) {		
	    if (!verifyCommonCouponAttributes(coupon)) return false;
	    logger.info("Common attributes passed");
	    if (!verifyPercentageDiscountCoupons(coupon)) return false;
		if (coupon.isDiscountOnLaborCharges()) return false;
		if (coupon.isDiscountOnMaterialCharges()) return false;
		if (!coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) != 0) return false;
		if (coupon.getMaxDiscountPercentage() != 0) return false;
		if (coupon.getDiscountPercentage() <= 0) return false;	
		return true;	
	}
	
	private boolean verifyAbsoluteDiscPercentLabour(Coupon coupon) {		
	    if (!verifyCommonCouponAttributes(coupon)) return false;
	    if (!verifyPercentageDiscountCoupons(coupon)) return false;
		if (coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.isDiscountOnMaterialCharges()) return false;
		if (!coupon.isDiscountOnLaborCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) != 0) return false;
		if (coupon.getMaxDiscountPercentage() != 0) return false;
		if (coupon.getDiscountPercentage() <= 0) return false;	
		return true;	
	}
	
	private boolean verifyAbsoluteDiscPercentMaterial(Coupon coupon) {		
	    if (!verifyCommonCouponAttributes(coupon)) return false;
	    if (!verifyPercentageDiscountCoupons(coupon)) return false;
		if (coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.isDiscountOnLaborCharges()) return false;
		if (!coupon.isDiscountOnMaterialCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) != 0) return false;
		if (coupon.getMaxDiscountPercentage() != 0) return false;
		if (coupon.getDiscountPercentage() <= 0) return false;	
		return true;	
	}
	
	//DISCOUNT_PERCENT_ON_TOTAL_WITH_MAX_AMOUNT
	
	private boolean verifyDiscountPercentOnTotalWthMaxDiscountAmount(Coupon coupon) {
		if (!verifyCommonCouponAttributes(coupon)) return false;
		if (!verifyPercentageDiscountCoupons(coupon)) return false;
		if (coupon.isDiscountOnLaborCharges()) return false;
		if (coupon.isDiscountOnMaterialCharges()) return false;
		if (!coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) != 0) return false;
		if (coupon.getMaxDiscountPercentage() <= 0) return false;
		if (coupon.getDiscountPercentage() <= 0) return false;	
		if (coupon.getMaximumDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		return true;		
	}
	
	private boolean verifyDiscountPercentOnLaborWthMaxDiscountAmount(Coupon coupon) {
		if (!verifyCommonCouponAttributes(coupon)) return false;
		if (!verifyPercentageDiscountCoupons(coupon)) return false;
		if (coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.isDiscountOnMaterialCharges()) return false;
		if (!coupon.isDiscountOnLaborCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) != 0) return false;
		if (coupon.getMaxDiscountPercentage() <= 0) return false;
		if (coupon.getDiscountPercentage() <= 0) return false;	
		if (coupon.getMaximumDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		return true;		
	}
	
	private boolean verifyDiscountPercentOnMaterialWthMaxDiscountAmount(Coupon coupon) {
		if (!verifyCommonCouponAttributes(coupon)) return false;
		if (!verifyPercentageDiscountCoupons(coupon)) return false;
		if (coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.isDiscountOnLaborCharges()) return false;
		if (!coupon.isDiscountOnMaterialCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) != 0) return false;
		if (coupon.getMaxDiscountPercentage() <= 0) return false;
		if (coupon.getDiscountPercentage() <= 0) return false;	
		if (coupon.getMaximumDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		return true;		
	}
	
	private boolean verifyAbsoluteCashDiscountOnTotal(Coupon coupon) {
		if (!verifyCommonCouponAttributes(coupon)) return false;
		if (!verifyCashDiscountCoupons(coupon)) return false;
		if (coupon.isDiscountOnMaterialCharges()) return false;
		if (coupon.isDiscountOnLaborCharges()) return false;
		if (!coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		if (coupon.getMaxDiscountPercentage() != 0) return false;
		if (coupon.getDiscountPercentage() != 0) return false;	
		if (coupon.getMaximumDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		return true;	
	}
	
	private boolean verifyAbsoluteCashDiscountOnLabor(Coupon coupon) {
		if (!verifyCommonCouponAttributes(coupon)) return false;
		if (!verifyCashDiscountCoupons(coupon)) return false;
		if (coupon.isDiscountOnMaterialCharges()) return false;
		if (!coupon.isDiscountOnLaborCharges()) return false;
		if (coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		if (coupon.getMaxDiscountPercentage() != 0) return false;
		if (coupon.getDiscountPercentage() != 0) return false;	
		if (coupon.getMaximumDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		return true;	
	}
	
	private boolean verifyAbsoluteCashDiscountOnMaterial(Coupon coupon) {
		if (!verifyCommonCouponAttributes(coupon)) return false;
		if (!verifyCashDiscountCoupons(coupon)) return false;
		if (!coupon.isDiscountOnMaterialCharges()) return false;
		if (coupon.isDiscountOnLaborCharges()) return false;
		if (coupon.isDiscountOnTotalCharges()) return false;
		if (coupon.getDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		if (coupon.getMaxDiscountPercentage() != 0) return false;
		if (coupon.getDiscountPercentage() != 0) return false;	
		if (coupon.getMaximumDiscountAmount().compareTo(BigDecimal.ZERO) <= 0) return false;
		return true;	
	}

}
