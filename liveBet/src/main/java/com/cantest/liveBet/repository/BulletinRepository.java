package com.cantest.liveBet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.model.SportType;

public interface BulletinRepository extends JpaRepository<Bulletin, Long> {
	
	Optional<Bulletin> findByNameAndSportType(String name, SportType sportType);
}
