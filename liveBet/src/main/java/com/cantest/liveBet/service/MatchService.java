package com.cantest.liveBet.service;

import java.util.List;

import com.cantest.liveBet.model.Match;
import com.cantest.liveBet.model.MatchOddsHistory;
import com.cantest.liveBet.response.MatchResponse;

public interface MatchService {
	
	// POST
	public MatchResponse addMatchToBulletin(Long bulletinId, Match match);
	
	// GET
	public List<MatchResponse> findByBulletinId(Long bulletinId);
	public List<MatchOddsHistory> findByMatchIdOrderByUpdateTimeAsc(Long matchId);
	public List<Match> findByIdIn(List<Long> matchIds);

}
