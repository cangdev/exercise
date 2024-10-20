package com.cantest.liveBet.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cantest.liveBet.component.QueueComponent;
import com.cantest.liveBet.exception.ExceptionMessage;
import com.cantest.liveBet.mapper.CouponMapper;
import com.cantest.liveBet.model.Coupon;
import com.cantest.liveBet.model.MatchBet;
import com.cantest.liveBet.service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CouponController.class)
public class CouponControllerTest4 {
	

	@MockBean
	private CouponService couponService;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private CouponMapper couponMapper;
	
	@MockBean
	private QueueComponent queueComponent;
	
	@MockBean
	private ExceptionMessage exceptionMessage;;
	
	
	@Test
    public void testCreateCoupon() throws Exception {
		
		MatchBet matchBet1 = MatchBet.builder().matchId(1L).betId(1).build();
		MatchBet matchBet2 = MatchBet.builder().matchId(2L).betId(0).build();
		MatchBet matchBet3 = MatchBet.builder().matchId(3L).betId(2).build();

		ArrayList<MatchBet> matchBetList = new ArrayList<MatchBet>();
		matchBetList.add(matchBet1);
		matchBetList.add(matchBet2);
		matchBetList.add(matchBet3);
		
		// Create a Mock Coupon
		Coupon coupon = new Coupon();
		coupon.setStake(100);
		coupon.setMatchBets(matchBetList);
		coupon.setQuantity(1);

		when(couponService.createCoupon(coupon)).thenReturn(coupon);
		
		
		mockMvc.perform(post("/api/coupons")
				.contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(coupon)))
                .andExpect(status().isCreated());


	}

}
