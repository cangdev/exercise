package com.cantest.liveBet.component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cantest.liveBet.model.CouponStatus;
import com.cantest.liveBet.queue.CouponExpiryObject;
import com.cantest.liveBet.repository.CouponRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class CouponExpireTask {

	private final ExecutorService executorService;
	
	private final QueueComponent queueComponent;
	
	private final CouponRepository couponRepository;
	
	@Autowired
	public CouponExpireTask(QueueComponent queueComponent, CouponRepository couponRepository) {
		this.executorService = Executors.newFixedThreadPool(1);
		this.queueComponent = queueComponent;
		this.couponRepository = couponRepository;
	}
	
	@PostConstruct
	private void init() {
		executorService.execute(new ProcessorThread());
	}
	
	
	@PreDestroy
	public void cleanup() {
	    executorService.shutdown();
	}

	private class ProcessorThread implements Runnable {
		
		@Override
		public void run() {
			
			while(true) {
				
				CouponExpiryObject expiredCoupon = queueComponent.takeFromCouponExpiryQueue();
				if(expiredCoupon != null) {
					expiredCoupon.getData().setCouponStatus(CouponStatus.EXPIRED);
					couponRepository.save(expiredCoupon.getData());
				}
				
			}
			
		}
	}
    
    
}
