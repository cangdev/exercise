package com.cantest.liveBet.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMessage {

	private final MessageSource messageSource;
	
	public ExceptionMessage(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public String getBulletinAlreadyExistExceptionMessage(Locale locale) {
		return messageSource.getMessage("bulletin.already.exist.msg", null, locale);
	}
	
	public String getBulletinNotFoundExceptionMessage(Locale locale) {
		return messageSource.getMessage("bulletin.not.found.msg", null, locale);
	}
	
	public String getCouponAlreadyExistExceptionMessage(Locale locale) {
		return messageSource.getMessage("coupon.already.exist.msg", null, locale);
	}
	
	public String getMaxCouponExceptionMessage(Locale locale) {
		return messageSource.getMessage("max.coupon.err.msg", null, locale);
	}
	
	public String getMinCouponExceptionMessage(Locale locale) {
		return messageSource.getMessage("min.coupon.err.msg", null, locale);
	}
	
	public String getCouponNotFoundExceptionMessage(Locale locale) {
		return messageSource.getMessage("coupon.not.found", null, locale);
	}
	
	public String getCouponExpiredExceptionMessage(Locale locale) {
		return messageSource.getMessage("coupon.expired", null, locale);
	}
	
}
