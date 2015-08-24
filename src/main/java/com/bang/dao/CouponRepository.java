package com.bang.dao;



import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bang.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Coupon findByStartDate(DateTime startDate);
	
	List<Coupon> findByValidityPeriodDays(int days);
	
	Coupon findByCouponCode(String couponCode);
	
}
