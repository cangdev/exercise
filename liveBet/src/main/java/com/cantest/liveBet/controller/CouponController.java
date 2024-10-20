package com.cantest.liveBet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cantest.liveBet.component.QueueComponent;
import com.cantest.liveBet.exception.CouponAlreadyExistException;
import com.cantest.liveBet.exception.CouponAlreadyPlacedException;
import com.cantest.liveBet.exception.CouponNotFoundException;
import com.cantest.liveBet.exception.ExceptionMessage;
import com.cantest.liveBet.mapper.CouponMapper;
import com.cantest.liveBet.model.Coupon;
import com.cantest.liveBet.queue.CouponExpiryObject;
import com.cantest.liveBet.request.CouponRequest;
import com.cantest.liveBet.request.PlaceBetRequest;
import com.cantest.liveBet.service.CouponService;
import com.cantest.liveBet.util.ResourceURL;
import com.cantest.liveBet.util.SystemConstants;

@RestController
@RequestMapping(ResourceURL.COUPONS)
public class CouponController {

	private final CouponService couponService;
	private final CouponMapper couponMapper;

	private final QueueComponent queueComponent;
	private final ExceptionMessage exceptionMessage;
	
	private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    public CouponController(CouponService couponService, CouponMapper couponMapper, QueueComponent queueComponent, ExceptionMessage exceptionMessage) {
        this.couponService = couponService;
        this.couponMapper = couponMapper;
        this.queueComponent = queueComponent;
        this.exceptionMessage = exceptionMessage;
    }

    // Create Coupon
    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponRequest couponRequest) {
    	
    	logger.info("New Coupon Create Request. bulletin: {} ", couponRequest);
    	
        try {
        	
        	Coupon coupon = couponMapper.mapToCoupon(couponRequest);
        	Coupon createdCoupon = couponService.createCoupon(coupon);
        	
            CouponExpiryObject obj = new CouponExpiryObject(createdCoupon, SystemConstants.COUPON_ROLLBACK_DURATION);
            queueComponent.addToCouponExpiryQueue(obj);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
	@PostMapping("/placeBet")
	public ResponseEntity<Coupon> placeBet(@RequestBody PlaceBetRequest request) {
		
		logger.info("New Place Bet Request. couponId: {} ", request.getCouponId());
		
		Coupon confirmedCoupon = couponService.placeBet(request.getCouponId());
		return ResponseEntity.ok(confirmedCoupon);
		
	}

    // Get All Coupons
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
    	logger.info("New getAllCoupons Request.");
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

	// Get Coupon By Id
	@GetMapping("/{id}")
	public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {

		logger.info("New getCouponById Request. couponId: {} ", id);

		Coupon coupon = couponService.getCouponById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						exceptionMessage.getCouponNotFoundExceptionMessage(LocaleContextHolder.getLocale()) + id));

		return ResponseEntity.ok(coupon);
	}

    // Delete Coupon
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
    	
    	logger.info("New deleteCoupon Request. couponId: {} ", id);
    	
        if (couponService.deleteCoupon(id)) {
            return ResponseEntity.noContent().build(); // 204 SUCCESSFUL, "No Content"
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }
    
    
    // Handles the CouponNotFoundException thrown when a coupon is placed.
    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<String> handleCouponNotFoundException(CouponNotFoundException ex) {
        logger.error("Error: {}", ex.getMessage());
        return ResponseEntity
        		.status(HttpStatus.NO_CONTENT) // 204 SUCCESSFUL, "No Content"
        		.body(ex.getMessage());
    }
    
    // Handles the CouponAlreadyExistException thrown when a coupon with the same couponId already exists.
    @ExceptionHandler(CouponAlreadyExistException.class)
    public ResponseEntity<String> handleCouponAlreadyExistException(CouponAlreadyExistException ex) {
        logger.error("Error: {}", ex.getMessage());
        return ResponseEntity
        		.status(HttpStatus.CONFLICT) // 409 Conflict
        		.body(ex.getMessage());
    }
    
    // Handles the CouponAlreadyPlacedException thrown when a coupon has already placed (if CouponStatus is Confirmed).
    @ExceptionHandler(CouponAlreadyPlacedException.class)
    public ResponseEntity<String> handleCouponAlreadyPlacedException(CouponAlreadyPlacedException ex) {
    	logger.error("Error: {}", ex.getMessage());
    	return ResponseEntity
    			.status(HttpStatus.CONFLICT) // 409 Conflict
    			.body(ex.getMessage());
    }
}
