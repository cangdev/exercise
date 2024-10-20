package com.cantest.liveBet.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  				// Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
@Entity
public class Coupon {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double stake; 				// Fee to bet

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "matchbet_id")
    private List<MatchBet> matchBets;	// Chosen Matches & Odds

    private int quantity; 				// Number of times the same coupon will be played
    
    @Enumerated(EnumType.STRING) 		// Specifies that the enum should be stored as a string
    private CouponStatus couponStatus; 	// Current status of the coupon

    private LocalDateTime createdAt; 	// Timestamp for coupon creation
    private long couponTime;			// Timestamp in Long Format for coupon creation
    
    @Version
    private Long version = 0L;			// Saves to db maximum version
    	
    // Constructor that initializes status and createdAt
    public Coupon(/*List<Match> matches,*/ List<MatchBet> matchBets, double stake, int quantity) {
//        this.matches 		= matches;
        this.matchBets 		= matchBets;
        this.stake 			= stake;
        this.quantity 		= quantity;
        this.couponStatus 	= CouponStatus.PENDING;			// Coupon starts in PENDING state
        this.createdAt 		= LocalDateTime.now(); 			// Set creation time
        this.couponTime		= System.currentTimeMillis();	// Set creation time
    }
}
