package com.cantest.liveBet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cantest.liveBet.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> findByBulletinId(Long bulletinId);
	List<Match> findByIdIn(List<Long> matchIds);
}
