package com.cantest.liveBet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.REQUEST_TIMEOUT)
public class CouponExpiredException extends RuntimeException {
	
	private static final long serialVersionUID = -2842700392921163638L;

	public CouponExpiredException(String message){
        super(message);
    }
	
}
