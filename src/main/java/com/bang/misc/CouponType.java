package com.bang.misc;

public enum CouponType {
	
	DUMMY("dummy"),
	ABSOLUTE_DISCOUNT_PERCENT_ON_TOTAL("absolute_discount_percent_on_total"),
	ABSOLUTE_DISCOUNT_PERCENT_ON_LABOUR("absolute_discount_percent_on_labour"),
	ABSOLUTE_DISCOUNT_PERCENT_ON_MATERIAL("absolute_discount_percent_on_material"),
	ABSOLUTE_DISCOUNT_CASH_ON_TOTAL("absolute_discount_cash_on_total"),
	ABSOLUTE_DISCOUNT_CASH_ON_LABOUR("absolute_discount_cash_on_labour"),
	ABSOLUTE_DISCOUNT_CASH_ON_MATERIAL("absolute_discount_cash_on_material"),
	ABSOLUTE_CASH_BACK_ON_TOTAL("absolute_cash_back_on_total"),
	ABSOLUTE_CASH_BACK_ON_LABOUR("absolute_cash_back_on_labour"),
	ABSOLUTE_CASH_BACK_ON_MATERIAL("absolute_cash_back_on_material"),	
	DISCOUNT_PERCENT_ON_TOTAL_WITH_MAX_AMOUNT("discount_percent_on_total_with_max_amount"),
	DISCOUNT_PERCENT_ON_LABOUR_WITH_MAX_AMOUNT("discount_percent_on_labour_with_max_amount"),
	DISCOUNT_PERCENT_ON_MATERIAL_WITH_MAX_AMOUNT("discount_percent_on_material_with_max_amount"),
	/*CASH_BACK_ON_TOTAL_WITH_MAX_AMOUNT("cash_back_on_total_with_max_amount"),
	CASH_BACK_ON_LABOUR_WITH_MAX_AMOUNT("cash_back_on_labour_with_max_amount"),
	CASH_BACK_ON_MATERIAL_WITH_MAX_AMOUNT("cash_back_on_material_with_max_amount")*/;
	
	String couponType;
	
	CouponType(String couponType) {
		this.couponType = couponType;
	}
	
	public String getCouponType() {
		return this.couponType;
	}

}
