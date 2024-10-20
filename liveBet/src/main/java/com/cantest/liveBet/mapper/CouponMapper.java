package com.cantest.liveBet.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cantest.liveBet.model.BetType;
import com.cantest.liveBet.model.Coupon;
import com.cantest.liveBet.model.MatchBet;
import com.cantest.liveBet.request.CouponRequest;
import com.cantest.liveBet.request.MatchBetRequest;

@Component
public class CouponMapper {
	
	public Coupon mapToCoupon(CouponRequest couponRequest) {
		
		List<MatchBetRequest> matchBetRequests = couponRequest.getMatchBets();
		List<MatchBet> matchBets = new ArrayList<MatchBet>();
		
		for (MatchBetRequest matchBetRequest : matchBetRequests) {
			MatchBet matchBet = new MatchBet();
			String betType = matchBetRequest.getBetType();
			BetType bet = BetType.fromString(betType);
			
			matchBet.setMatchId(matchBetRequest.getMatchId());
//			matchBet.setBetType(bet);								// HOME_WIN=0, DRAW=1, AWAY_WIN=2
			matchBet.setBetId(bet.getValue());						// HOME_WIN=1, DRAW=0, AWAY_WIN=2
			matchBets.add(matchBet);
		}
		
		return new Coupon(matchBets, couponRequest.getStake(), couponRequest.getQuantity());
		
    }
	
	
	
}
