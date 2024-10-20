package com.cantest.liveBet.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  				// Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
public class CouponRequest {

    private double stake; 							// Fee to bet
    private List<MatchBetRequest> matchBets;
    private int quantity; 							// Number of times the same coupon will be played

}
