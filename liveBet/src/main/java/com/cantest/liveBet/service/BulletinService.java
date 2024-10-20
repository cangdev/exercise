package com.cantest.liveBet.service;

import java.util.List;
import java.util.Optional;

import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.request.BulletinRequest;

public interface BulletinService {

	// POST
	public Bulletin addBulletin(BulletinRequest bulletinRequest);
    
	// GET
	public List<Bulletin> getAllBulletins();
    public Optional<Bulletin> getBulletinById(Long id);

    // DELETE
    public boolean deleteBulletin(Long id);
    
}
