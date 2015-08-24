package com.bang.misc;

import java.math.BigDecimal;

import com.bang.model.Coupon;
import com.bang.model.Invoice;

public class DiscountCalculator {
	
	private static BigDecimal calculateDiscountedCharge(BigDecimal charge, int discountPercent) {
		if (discountPercent < 0 || charge.compareTo(BigDecimal.ZERO) <= 0) return charge;
		BigDecimal discountAmount = charge.multiply(new BigDecimal(String.valueOf(discountPercent)));
		discountAmount = discountAmount.divide(new BigDecimal("100"));
		return charge.subtract(discountAmount);
	}
	
	private static BigDecimal calculateDiscountedChargeWithMaxDiscount(BigDecimal charge, BigDecimal maximumDiscountAmount, int discountPercent) {
		BigDecimal discountedCharge = calculateDiscountedCharge(charge, discountPercent);
		return discountedCharge.compareTo(maximumDiscountAmount) > 0 ? maximumDiscountAmount : discountedCharge;
	}
	
	private static BigDecimal calculateDiscountedCharge(BigDecimal charge, BigDecimal discountAmount) {
		if (discountAmount.compareTo(BigDecimal.ZERO) < 0 || charge.compareTo(discountAmount) <= 0) return charge;
		return charge.subtract(discountAmount);
	}
	
	public static Invoice applyAbsoluteDiscPercentTotal(Invoice inv, Coupon cpn) {
		BigDecimal discountedTotalCharges = calculateDiscountedCharge(inv.getTotalCharges(), cpn.getDiscountPercentage());
		inv.setDiscountedTotalCharges(discountedTotalCharges);
		return inv;		
	}
	
	public static Invoice applyAbsoluteDiscPercentLabour(Invoice inv, Coupon cpn) {
		BigDecimal discountedLabourCharges = calculateDiscountedCharge(inv.getLabourCharges(), cpn.getDiscountPercentage());
		inv.setDiscountedLabourCharges(discountedLabourCharges);
		return inv;
	}
	
	public static Invoice applyAbsoluteDiscPercentMaterial(Invoice inv, Coupon cpn) {
		BigDecimal discountedMaterialCharges = calculateDiscountedCharge(inv.getMaterialCharges(), cpn.getDiscountPercentage());
		inv.setDiscountedMaterialCharges(discountedMaterialCharges);
		return inv;
	}
	
	public static Invoice applyDiscountPercentOnTotalWithMaxDiscountAmount(Invoice inv, Coupon cpn) {
		BigDecimal discountedTotalCharges = calculateDiscountedChargeWithMaxDiscount(inv.getTotalCharges(), cpn.getMaximumDiscountAmount(), cpn.getDiscountPercentage());
		inv.setDiscountedTotalCharges(discountedTotalCharges);
		return inv;
	}
	
	public static Invoice applyDiscountPercentOnLaborWithMaxDiscountAmount(Invoice inv, Coupon cpn) {
		BigDecimal discountedLabourCharges = calculateDiscountedChargeWithMaxDiscount(inv.getLabourCharges(), cpn.getMaximumDiscountAmount(), cpn.getDiscountPercentage());
		inv.setDiscountedLabourCharges(discountedLabourCharges);
		return inv;
	}
	
	public static Invoice applyDiscountPercentOnMaterialWithMaxDiscountAmount(Invoice inv, Coupon cpn) {
		BigDecimal discountedMaterialCharges = calculateDiscountedChargeWithMaxDiscount(inv.getMaterialCharges(), cpn.getMaximumDiscountAmount(), cpn.getDiscountPercentage());
		inv.setDiscountedMaterialCharges(discountedMaterialCharges);
		return inv;
	}
	
	public static Invoice applyAbsoluteCashDiscountOnTotal(Invoice inv, Coupon cpn) {
		BigDecimal discountedTotalCharges = calculateDiscountedCharge(inv.getTotalCharges(), cpn.getDiscountAmount());
		inv.setDiscountedTotalCharges(discountedTotalCharges);
		return inv;
	}
	
	public static Invoice applyAbsoluteCashDiscountOnLabor(Invoice inv, Coupon cpn) {
		BigDecimal discountedLaborCharges = calculateDiscountedCharge(inv.getLabourCharges(), cpn.getDiscountAmount());
		inv.setDiscountedLabourCharges(discountedLaborCharges);
		return inv;
	}
	
	public static Invoice applyAbsoluteCashDiscountOnMaterial(Invoice inv, Coupon cpn) {
		BigDecimal discountedMaterialCharges = calculateDiscountedCharge(inv.getMaterialCharges(), cpn.getDiscountAmount());
		inv.setDiscountedLabourCharges(discountedMaterialCharges);
		return inv;
	}
}
