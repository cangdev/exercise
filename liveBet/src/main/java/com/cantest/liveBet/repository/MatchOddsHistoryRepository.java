package com.cantest.liveBet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cantest.liveBet.model.MatchOddsHistory;

public interface MatchOddsHistoryRepository extends JpaRepository<MatchOddsHistory, Long> {
	
	List<MatchOddsHistory> findByMatchIdOrderByUpdateTimeAsc(Long matchId);

}
