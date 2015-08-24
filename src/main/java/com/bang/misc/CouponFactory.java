package com.bang.misc;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.bang.model.Coupon;

public class CouponFactory {
	
	private static Coupon coupon = new Coupon("", "", new DateTime(), 0, CouponType.ABSOLUTE_DISCOUNT_PERCENT_ON_TOTAL,
			false, false, false, false, false, 0, 0, new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new ArrayList<String>());
	
	public static Coupon createAbsoluteDiscountPercentOnTotalCharges() {
		ArrayList<String> appCities = new ArrayList<String>();
		appCities.add(0, "Bangalore");
		coupon.setApplicableCities(appCities);
		coupon.setDicountInPercent(true);
		coupon.setDiscountPercentage(10);
		coupon.setDiscountOnTotalCharges(true);
		return coupon;
	}

}
