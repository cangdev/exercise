package com.ing.broker.app.exception;

public class CustomerNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = -2798904896735433552L;
	
	private final String errorCode;

    public CustomerNotFoundException(String message) {
        super(message);
        this.errorCode = "CUSTOMER_NOT_FOUND";
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "CUSTOMER_NOT_FOUND";
    }

    public String getErrorCode() {
        return errorCode;
    }
}