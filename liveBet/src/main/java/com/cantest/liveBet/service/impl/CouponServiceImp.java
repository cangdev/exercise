package com.cantest.liveBet.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.cantest.liveBet.exception.CouponAlreadyPlacedException;
import com.cantest.liveBet.exception.CouponExpiredException;
import com.cantest.liveBet.exception.CouponNotFoundException;
import com.cantest.liveBet.exception.ExceptionMessage;
import com.cantest.liveBet.model.Coupon;
import com.cantest.liveBet.model.CouponStatus;
import com.cantest.liveBet.repository.CouponRepository;
import com.cantest.liveBet.service.CouponService;

@Service
public class CouponServiceImp implements CouponService {
	
	private final CouponRepository couponRepository;
	private final ExceptionMessage exceptionMessage;
	
	
	// Logger for this service
	private static final Logger logger = LoggerFactory.getLogger(CouponServiceImp.class);
    
    @Autowired
    public CouponServiceImp(CouponRepository couponRepository, ExceptionMessage exceptionMessage) {
        this.couponRepository = couponRepository;
        this.exceptionMessage = exceptionMessage;
    }
    
    public Coupon createCoupon(Coupon coupon) {
    	
    	validateCoupon(coupon);
    	coupon.setCouponStatus(CouponStatus.PENDING);
    	
        return couponRepository.save(coupon);
    }

	public synchronized Coupon placeBet(Long couponId) {
		Coupon coupon = getCouponById(couponId)
				.orElseThrow(() -> new CouponNotFoundException(exceptionMessage.getCouponNotFoundExceptionMessage(LocaleContextHolder.getLocale())));

		// COUPON_ALREADY_PLACED Control
		if (coupon.getCouponStatus().equals(CouponStatus.CONFIRMED)) {
			logger.error(exceptionMessage.getCouponAlreadyPlacedExceptionMessage(LocaleContextHolder.getLocale()) + coupon.getId());
			throw new CouponAlreadyPlacedException(exceptionMessage.getCouponAlreadyPlacedExceptionMessage(LocaleContextHolder.getLocale()) + coupon.getId());
		}

		// COUPON_EXPIRED Control
		if (coupon.getCouponStatus() == CouponStatus.EXPIRED) {
			logger.error(exceptionMessage.getCouponExpiredExceptionMessage(LocaleContextHolder.getLocale()) + coupon.getId());
			throw new CouponExpiredException(exceptionMessage.getCouponExpiredExceptionMessage(LocaleContextHolder.getLocale()) + coupon.getId());
		}

		coupon.setCouponStatus(CouponStatus.CONFIRMED);
		coupon = couponRepository.save(coupon);
		return coupon;

	}

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Optional<Coupon> getCouponById(Long couponId) {
        return couponRepository.findById(couponId);
    }

    public boolean deleteCoupon(Long id) {
        if (couponRepository.existsById(id)) {
            couponRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    
    // Validations
    private void validateCoupon(Coupon coupon) {
    	
        if (coupon.getQuantity() > 500) {
            throw new RuntimeException(exceptionMessage.getMaxCouponExceptionMessage(LocaleContextHolder.getLocale()));
        }
        
        if (coupon.getQuantity() < 1) {
            throw new RuntimeException(exceptionMessage.getMinCouponExceptionMessage(LocaleContextHolder.getLocale()));
        }
    }


}
