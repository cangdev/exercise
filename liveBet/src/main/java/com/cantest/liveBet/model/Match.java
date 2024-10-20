package com.cantest.liveBet.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Match {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String league;
	private String homeTeam;
	private String awayTeam;
	private double homeWinOdds;
	private double drawOdds;
	private double awayWinOdds;
	private LocalDateTime matchStartTime;
	
	@ManyToOne
    @JoinColumn(name = "bulletin_id")
    private Bulletin bulletin;
	
}
