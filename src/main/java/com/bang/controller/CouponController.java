package com.bang.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bang.controller.exception.BadCouponException;
import com.bang.controller.exception.CouponExistsException;
import com.bang.misc.CouponType;
import com.bang.model.Coupon;
import com.bang.service.CouponService;

/**
 * @author Vengat
 *
 */
@RestController
public class CouponController {
	
	private static final Logger logger = Logger.getLogger(CouponController.class);
	
	@Autowired
	CouponService service;
	
	@RequestMapping(value = "/coupons", method = RequestMethod.GET)
	public ResponseEntity<Coupon> get() {
		logger.info("In coupon controller. Get coupons");
		Coupon coupon = new Coupon("", "", new DateTime(), 0, CouponType.DUMMY,
				false, false, false, false, false, 0, 0, new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new ArrayList<String>());
		return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/coupons", method = RequestMethod.POST)
	public ResponseEntity<Coupon> create(@RequestBody Coupon coupon) throws BadCouponException, CouponExistsException {
		logger.info("Create coupon");
		Coupon cpn = service.create(coupon);
		return new ResponseEntity<Coupon>(cpn, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/coupons/couponCode/{couponCode}", method = RequestMethod.GET)
	public ResponseEntity<Coupon> getByCode(@PathVariable String couponCode) throws BadCouponException, CouponExistsException {
		logger.info("Get coupon by code");
		Coupon cpn = service.getCouponByCode(couponCode);
		return new ResponseEntity<Coupon>(cpn, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/coupons/expire/couponCode/{couponCode}", method = RequestMethod.GET)
	public ResponseEntity<Coupon> expireCoupon(@PathVariable String couponCode) throws BadCouponException, CouponExistsException {
		logger.info("Expire coupon");
		Coupon cpn = service.expireCouponByCode(couponCode);
		return new ResponseEntity<Coupon>(cpn, HttpStatus.OK);		
	}

}
