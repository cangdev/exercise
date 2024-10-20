package com.cantest.liveBet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cantest.liveBet.model.Match;
import com.cantest.liveBet.model.MatchOddsHistory;
import com.cantest.liveBet.response.MatchResponse;
import com.cantest.liveBet.service.MatchService;
import com.cantest.liveBet.util.ResourceURL;

@RestController
@RequestMapping(ResourceURL.MATCHES)
public class MatchController {

	private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }
    
    @PostMapping("/{bulletinId}")
    public ResponseEntity<MatchResponse> addMatchToBulletin(@PathVariable Long bulletinId, @RequestBody Match match) {
        MatchResponse addedMatch = matchService.addMatchToBulletin(bulletinId, match);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMatch);	
    }

    @GetMapping("/{bulletinId}")
    public List<MatchResponse> getMatchesByBulletinId(@PathVariable Long bulletinId) {
        return matchService.findByBulletinId(bulletinId);
    }
    
    @GetMapping("/{matchId}/odds-history")
    public List<MatchOddsHistory> getMatchOddsHistory(@PathVariable Long matchId) {
    	return matchService.findByMatchIdOrderByUpdateTimeAsc(matchId);
    }
    
}
