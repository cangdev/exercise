package com.cantest.liveBet.service;

import java.util.List;
import java.util.Optional;

import com.cantest.liveBet.model.Coupon;

public interface CouponService {
	
	// POST
	public Coupon createCoupon(Coupon coupon);
	public Coupon placeBet(Long couponId);
	
	// GET
	public List<Coupon> getAllCoupons();
	public Optional<Coupon> getCouponById(Long couponId);
	
	// DELETE
	public boolean deleteCoupon(Long id);

    
}
