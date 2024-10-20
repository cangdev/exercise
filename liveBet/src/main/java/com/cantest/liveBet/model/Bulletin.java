package com.cantest.liveBet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data  				// Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
@Builder
@Entity
public class Bulletin {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // BulletinName (eg: 17-20 Oct Bulletin)
    
    @Enumerated(EnumType.STRING)
    private SportType sportType; 

}
