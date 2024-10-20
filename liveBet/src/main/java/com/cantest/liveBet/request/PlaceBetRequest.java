package com.cantest.liveBet.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  				// Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
public class PlaceBetRequest {

	private Long couponId;	//Coupon ID
	
}
