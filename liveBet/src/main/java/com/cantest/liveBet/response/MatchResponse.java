package com.cantest.liveBet.response;

import java.time.LocalDateTime;

import lombok.Data;


@Data	// @Data : Generates getters/setters, a constructor, toString, hashCode and equals.
public class MatchResponse {
	
	private String league;
	private String homeTeam;
	private String awayTeam;
	private double homeWinOdds;
	private double drawOdds;
	private double awayWinOdds;
	private LocalDateTime matchStartTime;
    private String bulletinName;

}
