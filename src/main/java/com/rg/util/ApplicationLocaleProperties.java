package com.rg.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class ApplicationLocaleProperties {

	public static final Locale defaultLocale = Locale.ENGLISH;    //Default locale

    private List<String> languages = new ArrayList<>();

    private List<String> countries = new ArrayList<>();

    public ApplicationLocaleProperties() {
    	
    	languages.add("en");
    	countries.add("");
    	
    	languages.add("ko");
    	countries.add("KR");
    	
    	languages.add("ja");
    	countries.add("JP");
    	
    	languages.add("zn");
    	countries.add("CN");
    	
    	languages.add("fr");
    	countries.add("FR");
    	
    }
    
    public Locale getLocale(int index) {
        return new Locale(languages.get(index), countries.get(index));
    }

    public List<String> getLanguages() {
    	return languages;
    }

    public List<String> getCountries() {
    	return countries;
    }
    
}
