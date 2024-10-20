package com.cantest.liveBet.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.model.Match;
import com.cantest.liveBet.model.SportType;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MatchRepositoryTest {
	
	@Autowired
	MatchRepository matchRepository;
	
	@Test
	public void matchRepositorySaveAndReturnSavedMatch() {
		
		//Arrange
		Bulletin bulletin = Bulletin.builder()
				.name("24-27 Ekim Bulteni")
				.sportType(SportType.FOOTBALL).build();
		
		Match match = Match.builder()
				.league("Trendyol Super Lig")
				.homeTeam("Besiktas")
				.awayTeam("Konyaspor")
				.homeWinOdds(1.26)
				.drawOdds(4.16)
				.awayWinOdds(6.35)
				.matchStartTime(LocalDateTime.parse("2024-10-20T16:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.bulletin(bulletin)
				.build();
		
		
		//Act
		Match savedMatch = matchRepository.save(match);
		
		//Assert
		Assertions.assertThat(savedMatch).isNotNull();
		Assertions.assertThat(savedMatch.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void matchRepositoryReturnMoreThanOneMatch() {
		
		Match match1 = Match.builder()
				.league("Trendyol Super Lig")
				.homeTeam("Besiktas")
				.awayTeam("Konyaspor")
				.homeWinOdds(1.26)
				.drawOdds(4.16)
				.awayWinOdds(6.35)
				.matchStartTime(LocalDateTime.parse("2024-10-20T16:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.build();

		Match match2 = Match.builder()
				.league("Premier League")
				.homeTeam("Manchester United")
				.awayTeam("Brentford")
				.homeWinOdds(1.50)
				.drawOdds(3.73)
				.awayWinOdds(3.96)
				.matchStartTime(LocalDateTime.parse("2024-10-19T17:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.build();
		
		Match match3 = Match.builder()
				.league("La Liga")
				.homeTeam("Barcelona")
				.awayTeam("Sevilla")
				.homeWinOdds(1.16)
				.drawOdds(5.16)
				.awayWinOdds(7.52)
				.matchStartTime(LocalDateTime.parse("2024-10-20T22:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.build();
		
		
		matchRepository.save(match1);
		matchRepository.save(match2);
		matchRepository.save(match3);
		
		List<Long> matchIds = new ArrayList<>();
		matchIds.add(match1.getId());
		matchIds.add(match2.getId());
		matchIds.add(match3.getId());
		
		List<Match> matchList = matchRepository.findByIdIn(matchIds);
		
		Assertions.assertThat(matchList).isNotNull();
		Assertions.assertThat(matchList.size()).isEqualTo(3);
		
	}
	


}
