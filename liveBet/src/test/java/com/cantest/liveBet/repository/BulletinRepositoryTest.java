package com.cantest.liveBet.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.model.SportType;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BulletinRepositoryTest {
	
	@Autowired
	BulletinRepository bulletinRepository;
	
	@Test
	public void bulletinRepositorySaveAndReturnSavedBulletin() {
		
		//Arrange
		Bulletin bulletin = Bulletin.builder()
				.name("17-20 Ekim Bulteni")
				.sportType(SportType.FOOTBALL).build();
		
		
		//Act
		Bulletin savedBulletin = bulletinRepository.save(bulletin);
		
		//Assert
		Assertions.assertThat(savedBulletin).isNotNull();
		Assertions.assertThat(savedBulletin.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void bulletinRepositoryReturnMoreThenOneBulletin() {
		
		Bulletin bulletin1 = Bulletin.builder()
				.name("17-20 Ekim Bulteni")
				.sportType(SportType.BASKETBALL).build();

		Bulletin bulletin2 = Bulletin.builder()
				.name("24-27 Ekim Bulteni")
				.sportType(SportType.FOOTBALL).build();
		
		
		bulletinRepository.save(bulletin1);
		bulletinRepository.save(bulletin2);
		
		List<Bulletin> bulletinList = bulletinRepository.findAll();
		
		Assertions.assertThat(bulletinList).isNotNull();
		Assertions.assertThat(bulletinList.size()).isEqualTo(2);
		
	}
	
	@Test
	public void bulletinRepositoryFindByIdReturnBulletin() {
		
		Bulletin bulletin = Bulletin.builder()
				.id(99L)
				.name("17-20 Ekim Bulteni")
				.sportType(SportType.BASKETBALL).build();
		
		bulletinRepository.save(bulletin);
		
		Optional<Bulletin> bulletinFromDB = bulletinRepository.findById(bulletin.getId());

		Assertions.assertThat(bulletinFromDB).isNotNull();
		
	}
	
	@Test
	public void bulletinRepositoryFindByNameAndSportTypeReturnBulletin() {
		
		Bulletin bulletin = Bulletin.builder()
				.name("17-20 Ekim Bulteni")
				.sportType(SportType.FOOTBALL).build();
		
		bulletinRepository.save(bulletin);
		
		Optional<Bulletin> bulletinFromDB = bulletinRepository.findByNameAndSportType(bulletin.getName(), bulletin.getSportType());
		
		Assertions.assertThat(bulletinFromDB).isNotNull();
		Assertions.assertThat(bulletinFromDB.get().getName()).isNotNull();
		Assertions.assertThat(bulletinFromDB.get().getSportType()).isNotNull();
		
	}
	
	
	@Test
	public void bulletinRepositoryDeleteBulletin() {
		
		Bulletin bulletin = Bulletin.builder()
				.id(1L)
				.name("17-20 Ekim Bulteni")
				.sportType(SportType.FOOTBALL).build();
		
		bulletinRepository.save(bulletin);
		
		bulletinRepository.deleteById(bulletin.getId());
		Optional<Bulletin> bulletinFromDB = bulletinRepository.findById(bulletin.getId());
		
		Assertions.assertThat(bulletinFromDB).isEmpty();
		
	}

}
