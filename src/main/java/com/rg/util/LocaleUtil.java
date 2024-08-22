package com.rg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component("localeUtil")
public class LocaleUtil {
	
	private final Logger logger = LogManager.getLogger(LocaleUtil.class);
	
	//@Autowired
	//LocaleResolver localeResolver;
	
	public void setLocale(String language, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("########################## Current locale / getPathInfo : " + request.getPathInfo());
		logger.debug("########################## Current locale : " + language);
		
		Locale locale = new Locale(language);
		
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		if (localeResolver == null) {
			logger.debug("####### localeResolver is null.");
		}
		//localeResolver.setLocale(request, response, locale);
		
		LocaleContextHolder.setLocale(locale);
		
		//setLocale(locale);
	}
	
	public Locale getLocale() {
		
		Locale locale = LocaleContextHolder.getLocale();
		
		logger.debug("########################################## Current locale : " + locale.getLanguage());
		return locale;
	}

	
	/*
    private static final List<Locale> acceptableLocales = new ArrayList<>();

    @Autowired
    public LocaleUtil() {
    	ApplicationLocaleProperties applicationLocaleProperties = new ApplicationLocaleProperties();
        List<String> acceptableLanguages = applicationLocaleProperties.getLanguages();
        if (!CollectionUtils.isEmpty(acceptableLanguages)) {
            for (int i = 0, length = applicationLocaleProperties.getLanguages().size(); i < length; i++) {
                acceptableLocales.add(applicationLocaleProperties.getLocale(i));
            }
        }
    }

    public void setLocale(Locale locale) {
        if (isPossibleLocale(locale)) {
            logger.info("Change system locale " + locale);
            LocaleContextHolder.setLocale(locale);
        }
    }

    public Locale getLocale() {
        if (isPossibleLocale(LocaleContextHolder.getLocale())) {
            return LocaleContextHolder.getLocale();
        } else {
            return ApplicationLocaleProperties.defaultLocale;
        }
    }

    **
     * 현재 locale이 System에서지원하는 다국어 목록에 존재하는지 여부를 반환한다
     *
     * @param currentLocale 요청 받은 locale
     * @return boolean
     *
    private boolean isPossibleLocale(Locale currentLocale) {
        return acceptableLocales.contains(currentLocale); //Locale 객체거 equals, hashCode 메소드를 잘 구현하여 가능
    }
    */
}
