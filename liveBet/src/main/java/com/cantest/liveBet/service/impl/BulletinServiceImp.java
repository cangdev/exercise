package com.cantest.liveBet.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.cantest.liveBet.exception.BulletinAlreadyExistException;
import com.cantest.liveBet.exception.ExceptionMessage;
import com.cantest.liveBet.mapper.BulletinMapper;
import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.repository.BulletinRepository;
import com.cantest.liveBet.request.BulletinRequest;
import com.cantest.liveBet.service.BulletinService;

@Service
public class BulletinServiceImp implements BulletinService {

	private final BulletinRepository bulletinRepository;
	private final BulletinMapper bulletinMapper;
	
	private final ExceptionMessage exceptionMessage;
	
	// Logger for this service
    private static final Logger logger = LoggerFactory.getLogger(BulletinServiceImp.class);

    @Autowired
    public BulletinServiceImp(BulletinRepository bulletinRepository, BulletinMapper bulletinMapper, ExceptionMessage exceptionMessage) {
        this.bulletinRepository = bulletinRepository;
        this.bulletinMapper = bulletinMapper;
        this.exceptionMessage = exceptionMessage;
    }

    public List<Bulletin> getAllBulletins() {
        return bulletinRepository.findAll();
    }

    public Bulletin addBulletin(BulletinRequest bulletinRequest) {
    	logger.info("New Bulletin Request. bulletin:" + bulletinRequest.toString());
    	
    	Bulletin bulletin = bulletinMapper.mapToBulletin(bulletinRequest);
    	validateBulletin(bulletin);
    	
        return bulletinRepository.save(bulletin);
    }


	public Optional<Bulletin> getBulletinById(Long id) {
        return bulletinRepository.findById(id);
    }

    public boolean deleteBulletin(Long id) {
        if (bulletinRepository.existsById(id)) {
            bulletinRepository.deleteById(id);
            return true;
        }
        return false;
    }

    
    // Validations
    private void validateBulletin(Bulletin bulletin) {
    	
    	if (bulletinRepository.findByNameAndSportType(bulletin.getName(), bulletin.getSportType()).isPresent()) {
    		
//    		logger.error(ExceptionMessage.BULLETIN_ALREADY_EXIST_MSG + bulletin.toString());
//    		throw new BulletinAlreadyExistException(ExceptionMessage.BULLETIN_ALREADY_EXIST_MSG + bulletin.toString());
    		logger.error(exceptionMessage.getBulletinAlreadyExistExceptionMessage(LocaleContextHolder.getLocale()) + bulletin.toString());
    		throw new BulletinAlreadyExistException(exceptionMessage.getBulletinAlreadyExistExceptionMessage(LocaleContextHolder.getLocale()) + bulletin.toString());
    	}
    }
    
    
}
