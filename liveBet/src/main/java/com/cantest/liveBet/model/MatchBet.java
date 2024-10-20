package com.cantest.liveBet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MatchBet {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Long matchId;
	private Integer betId;			// HOME_WIN=0, DRAW=1, AWAY_WIN=2		==> Bet option for each match
//	private BetType betType;		// HOME_WIN=0, DRAW=1, AWAY_WIN=2		==> Bet option for each match

}
