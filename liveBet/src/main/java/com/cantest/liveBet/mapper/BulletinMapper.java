package com.cantest.liveBet.mapper;

import org.springframework.stereotype.Component;

import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.model.SportType;
import com.cantest.liveBet.request.BulletinRequest;

@Component
public class BulletinMapper {
	
	public Bulletin mapToBulletin(BulletinRequest bulletinRequest) {
		
		Bulletin bulletin = new Bulletin();
		bulletin.setName(bulletinRequest.getName());
		bulletin.setSportType(convertToSportType(bulletinRequest.getSportType()));
        return bulletin;
    }
	
	
	// Convert string sportType to SportType enum
	private SportType convertToSportType(String sportType) {
        
		if (sportType == null) {
			// Return null if input is null
            return null;
        }
        
		try {
			
			// Convert to enum
            return SportType.valueOf(sportType.toUpperCase());
            
        } catch (IllegalArgumentException e) {
        	
            // Throw an exception for invalid sport type
            throw new IllegalArgumentException("Invalid sport type: " + sportType);
            
        }
		
    }
	
	
	
	
}
