package com.cantest.liveBet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CouponAlreadyPlacedException extends RuntimeException {
	
	private static final long serialVersionUID = 7154314258265199263L;

	public CouponAlreadyPlacedException(String message){
        super(message);
    }
	
}
