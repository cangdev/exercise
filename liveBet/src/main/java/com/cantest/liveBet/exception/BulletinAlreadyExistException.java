package com.cantest.liveBet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class BulletinAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 2142593280907224349L;

	public BulletinAlreadyExistException(String message){
        super(message);
    }
	
}
