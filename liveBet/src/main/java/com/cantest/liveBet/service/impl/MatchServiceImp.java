package com.cantest.liveBet.service.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cantest.liveBet.exception.BulletinNotFoundException;
import com.cantest.liveBet.exception.ExceptionMessage;
import com.cantest.liveBet.mapper.MatchMapper;
import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.model.Match;
import com.cantest.liveBet.model.MatchOddsHistory;
import com.cantest.liveBet.repository.BulletinRepository;
import com.cantest.liveBet.repository.MatchOddsHistoryRepository;
import com.cantest.liveBet.repository.MatchRepository;
import com.cantest.liveBet.response.MatchResponse;
import com.cantest.liveBet.service.MatchService;
import com.cantest.liveBet.util.SystemConstants;

@Service
public class MatchServiceImp implements MatchService {
	
	private final MatchRepository matchRepository;
	private final MatchOddsHistoryRepository matchOddsHistoryRepository;
	private final BulletinRepository bulletinRepository;
	
	private final ExceptionMessage exceptionMessage;
	
    private final Random random = new Random();
    
    private static final DecimalFormat decimalFormat;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.'); 							// Set decimal separator as dot
        decimalFormat = new DecimalFormat("#.##", symbols);
    }
    
    
    // Logger for this service
    private static final Logger logger = LoggerFactory.getLogger(MatchServiceImp.class);

    @Autowired
    public MatchServiceImp(MatchRepository matchRepository, MatchOddsHistoryRepository matchOddsHistoryRepository, BulletinRepository bulletinRepository, ExceptionMessage exceptionMessage) {
        this.matchRepository = matchRepository;
        this.matchOddsHistoryRepository = matchOddsHistoryRepository;
        this.bulletinRepository = bulletinRepository;
        this.exceptionMessage = exceptionMessage;
    }
    
    // Method to update match odds every second
    @Scheduled(fixedRate = SystemConstants.COUPON_TIMEOUT_DURATION)
    public void updateMatchOdds() {
    	
        List<Match> matches = matchRepository.findAll(); 			// Fetch all matches from the database
        
        for (Match match : matches) {
        	
            // Update odds by adding a random value between -0.30 and 0.30
        	match.setHomeWinOdds(updateOddsByAddingRandom(match.getHomeWinOdds()));
            match.setDrawOdds(updateOddsByAddingRandom(match.getDrawOdds()));
            match.setAwayWinOdds(updateOddsByAddingRandom(match.getAwayWinOdds()));
            
            MatchOddsHistory matchOddsHistory = new MatchOddsHistory();
            matchOddsHistory.setMatch(match);
            matchOddsHistory.setHomeWinOdds(match.getHomeWinOdds());
            matchOddsHistory.setDrawOdds(match.getDrawOdds());
            matchOddsHistory.setAwayWinOdds(match.getAwayWinOdds());
            matchOddsHistory.setUpdateTime(LocalDateTime.now());
            matchOddsHistory.setBulletinId(match.getBulletin().getId());
            matchOddsHistoryRepository.save(matchOddsHistory);

            // Save updated match odds to the database
            matchRepository.save(match); 
            
            // Log the updated odds
            logger.info("Updated Match Odds - Home: {}, Draw: {}, Away: {}", 
            		match.getHomeWinOdds(), match.getDrawOdds(), match.getAwayWinOdds());
        }
    }
    
	public MatchResponse addMatchToBulletin(Long bulletinId, Match match) {

		Optional<Bulletin> bulletinOptional = bulletinRepository.findById(bulletinId);
		validateBulletin(bulletinOptional, bulletinId);

		Bulletin bulletin = bulletinOptional.get();
		match.setBulletin(bulletin);
		Match savedMatch = matchRepository.save(match); 			// so the primary key is not returned
		MatchResponse matchResponse = MatchMapper.mapToMatchResponse(savedMatch, new MatchResponse());

		// If Mac Added
		logger.info("Match '{}' between {} and {} added to Bulletin '{}'", 
				match.getId(), match.getHomeTeam(), match.getAwayTeam(), bulletinOptional.get().getName());

		return matchResponse;

	}
    
    // Generate new odds by adding a random value
    private double updateOddsByAddingRandom(double currentOdds) {
    	
    	double newOdds = currentOdds + generateRandomOdds();
        newOdds = Math.max(newOdds, 1.05); 							// To avoid falling below 1.05, if it falls below 1.00, it loses money.
        // TODO CAN Add validation for  
        return Double.parseDouble(decimalFormat.format(newOdds)); 	// Round to two decimal places

    }
    
    
    // Generates random odds between -0.30 and 0.30
    private double generateRandomOdds() {
    	return -0.30 + (0.30 - (-0.30)) * random.nextDouble();
    }



	public List<MatchResponse> findByBulletinId(Long bulletinId) {
		
		List<MatchResponse> responseMatchList = new ArrayList<MatchResponse>(); 
		
		List<Match> matchList = matchRepository.findByBulletinId(bulletinId);
		for (Match match : matchList) {
			responseMatchList.add(MatchMapper.mapToMatchResponse(match, new MatchResponse()));
		}
		return responseMatchList;
	}

	public List<MatchOddsHistory> findByMatchIdOrderByUpdateTimeAsc(Long matchId) {
		return matchOddsHistoryRepository.findByMatchIdOrderByUpdateTimeAsc(matchId);
	}
	
	// Validations
    private void validateBulletin(Optional<Bulletin> bulletinOptional, Long bulletinId) {
    	if (!bulletinOptional.isPresent()) {

			// Error log if bulletin not found
			logger.error("Bulletin with ID '{}' not found. Match could not be added.", bulletinId);

			// Throw Exception
			throw new BulletinNotFoundException(exceptionMessage.getBulletinNotFoundExceptionMessage(LocaleContextHolder.getLocale()) + bulletinId);
		}
    }


	@Override
	public List<Match> findByIdIn(List<Long> matchIds) {
		return matchRepository.findByIdIn(matchIds);
	}

}
