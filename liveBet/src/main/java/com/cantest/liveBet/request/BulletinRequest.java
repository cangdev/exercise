package com.cantest.liveBet.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data  				// Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all arguments
public class BulletinRequest {
	
    private String name;  			// BulletinName (eg: 17-20 Oct Bulletin)
    private String sportType; 

}
