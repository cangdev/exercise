package com.cantest.liveBet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cantest.liveBet.exception.BulletinAlreadyExistException;
import com.cantest.liveBet.exception.BulletinNotFoundException;
import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.request.BulletinRequest;
import com.cantest.liveBet.service.BulletinService;
import com.cantest.liveBet.util.ResourceURL;

@RestController
@RequestMapping(ResourceURL.BULLETINS)
public class BulletinController {
	
	private final BulletinService bulletinService;
	
	private static final Logger logger = LoggerFactory.getLogger(BulletinController.class);

    @Autowired
    public BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }
    
//	Get all bulletins
    @GetMapping
    public List<Bulletin> getAllBulletins() {
    	logger.info("New getAllBulletins Request.");
        return bulletinService.getAllBulletins();
    }

//	Add a new bulletin
    @PostMapping
    public ResponseEntity<Bulletin> addBulletin(@RequestBody BulletinRequest bulletinRequest) {
    	logger.info("New Bulletin Add Request. bulletin: {} ", bulletinRequest);
    	
    	Bulletin bulletin = bulletinService.addBulletin(bulletinRequest);
    	
        return ResponseEntity
        		.status(HttpStatus.CREATED)
        		.body(bulletin);
    }
    
    
//	Get a single bulletin by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bulletin> getBulletinById(@PathVariable Long id) {
    	logger.info("New Bulletin Get BulletinById Request. bulletinId: {} ", id);
    	
    	return bulletinService.getBulletinById(id)
                .map(bulletin -> ResponseEntity.status(HttpStatus.OK).body(bulletin)) 	// 200 OK
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 			// 404 Not Found
    }

//  Delete a bulletin by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBulletin(@PathVariable Long id) {
    	
    	logger.info("New deleteBulletin Request. bulletinId: {} ", id);
    	
        if (bulletinService.deleteBulletin(id)) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)	// 204 No Content. Success, no message body
                    .build();
        } else {
            return ResponseEntity
            		.status(HttpStatus.NOT_FOUND)	// 404 Not Found
            		.build(); 
        }
    }

    
    // Handles the BulletinAlreadyExistException thrown when a bulletin with the same name and sport type already exists.
    @ExceptionHandler(BulletinAlreadyExistException.class)
    public ResponseEntity<String> handleBulletinAlreadyExistException(BulletinAlreadyExistException ex) {
        logger.error("Error: {}", ex.getMessage());
        return ResponseEntity
        		.status(HttpStatus.CONFLICT) // 409 Conflict
        		.body(ex.getMessage());
    }
    
    // Handles the BulletinAlreadyExistException thrown when a bulletin with the same name and sport type already exists.
    @ExceptionHandler(BulletinNotFoundException.class)
    public ResponseEntity<String> handleBulletinNotFoundException(BulletinNotFoundException ex) {
        logger.error("Error: {}", ex.getMessage());
        return ResponseEntity
        		.status(HttpStatus.NOT_FOUND) // 404 Not Found
        		.body(ex.getMessage());
    }
    
    
}
