package com.cantest.liveBet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class CouponAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 3323717601128401192L;

	public CouponAlreadyExistException(String message){
        super(message);
    }
	
}
