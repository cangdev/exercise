package com.cantest.liveBet.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MatchOddsHistory {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="matchOddsHistory_id")
	private Long id;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;				// Not Need to return
	
	private double homeWinOdds;
	private double drawOdds;
	private double awayWinOdds;
	private LocalDateTime updateTime;
    private long bulletinId;
}
