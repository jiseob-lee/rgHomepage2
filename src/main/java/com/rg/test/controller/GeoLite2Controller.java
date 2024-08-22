package com.rg.test.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

@Controller
public class GeoLite2Controller {
	
	private final Logger logger = LogManager.getLogger(GeoLite2Controller.class);
	
	@RequestMapping("/rg/getCountry.do")
	@ResponseBody
	public Map<String, String> getCountry(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> map = new HashMap<String, String>();
		String countryStr = "";
		
		try {

			String GeoLite2Path = "D:/GeoLite2/GeoLite2-Country.mmdb";
			
			// A File object pointing to your GeoIP2 or GeoLite2 database
			if (new File("/home/ubuntu/GeoLite2").exists()) {
				GeoLite2Path = "/home/ubuntu/GeoLite2/GeoLite2-Country.mmdb";
			}
			
			File database = new File(GeoLite2Path);
			
			DatabaseReader reader = new DatabaseReader.Builder(database).build();
			
			InetAddress ipAddress = InetAddress.getByName(request.getParameter("ip"));

			// Replace "city" with the appropriate method for your database, e.g.,
			// "country".
			CountryResponse countryResponse = reader.country(ipAddress);

			Country country = countryResponse.getCountry();
			
			logger.debug(country.getIsoCode());
			logger.debug(country.getName());
			
			countryStr = country.getName();
			map.put("country", countryStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			e.printStackTrace();
		}
		
		//return countryStr;
		return map;
	}

}
