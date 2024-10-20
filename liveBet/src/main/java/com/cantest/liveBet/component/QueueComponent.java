package com.cantest.liveBet.component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

import org.springframework.stereotype.Component;

import com.cantest.liveBet.queue.CouponExpiryObject;

@Component
public class QueueComponent {
	
	
	private final BlockingQueue<CouponExpiryObject> couponExpiryQueue;
	
	public QueueComponent() {
		couponExpiryQueue = new DelayQueue<CouponExpiryObject>();
	}
	
	
	public void addToCouponExpiryQueue(CouponExpiryObject obj) {
		couponExpiryQueue.add(obj);
	}
	
	public CouponExpiryObject takeFromCouponExpiryQueue() {
		try {
			return couponExpiryQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteFromCouponExpiryQueue(CouponExpiryObject obj) {
		return couponExpiryQueue.remove(obj);
	}
	

}
