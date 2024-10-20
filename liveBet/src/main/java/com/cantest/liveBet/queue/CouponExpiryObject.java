package com.cantest.liveBet.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.cantest.liveBet.model.Coupon;

public class CouponExpiryObject implements Delayed {
	
	private Coupon 	data;
    private long 	startTime;

    public CouponExpiryObject(Coupon data, long delayInMilliseconds) {
        this.data = data;
        this.startTime = System.currentTimeMillis() + delayInMilliseconds;
    }
    
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    
    @Override
	public int compareTo(Delayed o) {
		if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)){
	        return -1;
	      }
	      if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)){
	        return 1;
	      }
	      return 0; 
	}

    
    @Override
	public boolean equals(Object obj) {
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    
	    if(data.getId() == null )
	    	return false;
	    
		return data.getId().equals(((CouponExpiryObject)obj).getData().getId());
	}
    
    public Coupon getData() {
		return data;
	}
	

}
