package com.cantest.liveBet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class BulletinNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 2142593280907224349L;

	public BulletinNotFoundException(String message){
        super(message);
    }
	
}
