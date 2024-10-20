package com.cantest.liveBet.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cantest.liveBet.component.QueueComponent;
import com.cantest.liveBet.exception.ExceptionMessage;
import com.cantest.liveBet.mapper.CouponMapper;
import com.cantest.liveBet.request.CouponRequest;
import com.cantest.liveBet.request.MatchBetRequest;
import com.cantest.liveBet.service.CouponService;

@SpringBootTest
public class CouponControllerTest {
	
	@InjectMocks
	private CouponController couponController;

	@Mock
	private CouponService couponService;
	
	@Mock
	private CouponMapper couponMapper;
	
	@Mock
	private QueueComponent queueComponent;
	
	@Mock
	private ExceptionMessage exceptionMessage;
	
	
	@Test
    public void testCreateCoupon() throws Exception {
		
		MatchBetRequest matchBetRequest1 = MatchBetRequest.builder().matchId(1L).betType("HOME_WIN").build();
		MatchBetRequest matchBetRequest2 = MatchBetRequest.builder().matchId(2L).betType("DRAW").build();
		MatchBetRequest matchBetRequest3 = MatchBetRequest.builder().matchId(3L).betType("AWAY_WIN").build();
		ArrayList<MatchBetRequest> matchBetRequestList = new ArrayList<MatchBetRequest>();
		matchBetRequestList.add(matchBetRequest1);
		matchBetRequestList.add(matchBetRequest2);
		matchBetRequestList.add(matchBetRequest3);
		
		// Create a Request to create a CouponRequest
		CouponRequest couponRequest = new CouponRequest();
		couponRequest.setStake(100);
		couponRequest.setMatchBets(matchBetRequestList);
		couponRequest.setQuantity(1);
		
		System.out.println(couponRequest);
		System.out.println(couponRequest);

		// Act: Call the createCustomer method in the controller
		ResponseEntity<?> responseEntity = couponController.createCoupon(couponRequest);
		System.out.println(responseEntity);

		// Then: Verify the response
		assertNotNull(responseEntity);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	}


}
