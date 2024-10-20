package com.cantest.liveBet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NO_CONTENT)
public class CouponNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -5756707215016065642L;

	public CouponNotFoundException(String message){
        super(message);
    }
	
}
