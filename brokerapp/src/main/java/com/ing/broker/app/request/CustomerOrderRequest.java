package com.ing.broker.app.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderRequest {

    private long 			customerId;
    private String 			asset;
    private String 			side;  		// BUY or SELL
    private int 			size;  		// Number of shares
    private double 			price;  	// Price per share
    private String 			status;  	// PENDING, MATCHED, CANCELED
    private LocalDateTime 	orderDate;

}
