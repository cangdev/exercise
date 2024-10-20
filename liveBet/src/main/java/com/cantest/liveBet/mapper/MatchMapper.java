package com.cantest.liveBet.mapper;

import com.cantest.liveBet.model.Match;
import com.cantest.liveBet.response.MatchResponse;

public class MatchMapper {
	
	public static MatchResponse mapToMatchResponse(Match match, MatchResponse matchResponse) {
		
		matchResponse.setLeague(match.getLeague());
		matchResponse.setHomeTeam(match.getHomeTeam());
		matchResponse.setAwayTeam(match.getAwayTeam());
		matchResponse.setHomeWinOdds(match.getHomeWinOdds());
		matchResponse.setDrawOdds(match.getDrawOdds());
		matchResponse.setAwayWinOdds(match.getAwayWinOdds());
		matchResponse.setMatchStartTime(match.getMatchStartTime());
		matchResponse.setBulletinName(match.getBulletin().getName());

        return matchResponse;
    }
	
}
