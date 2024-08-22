package com.rg.test.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

@Controller
public class ImageCheckController {
	
	private final Logger logger = LogManager.getLogger(ImageCheckController.class);
	
	@RequestMapping("/checkImage.do")
	//@ResponseBody
	public ModelAndView getCountry(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> map = new HashMap<String, String>();
		String countryStr = "";
		ModelAndView mav = new ModelAndView();
		/*
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
		*/
		String contentType = "";
		boolean image = false;
		try {
			URLConnection connection = new URL("http://jisblee.me/rg/DSC00080_03.jpg").openConnection();
			contentType = connection.getHeaderField("Content-Type");
			logger.info("############## contentType : " + contentType);
			if (contentType != null) {
				image = contentType.startsWith("image/");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return countryStr;
		try {
			mav.addObject("contentType", contentType);
			mav.addObject("image", image);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String contentType2 = "";
		boolean image2 = false;
		try {
			URLConnection connection2 = new URL("http://jisblee.me/assets/images/DSC00080_03.jpg").openConnection();
			contentType2 = connection2.getHeaderField("Content-Type");
			logger.info("############## contentType 2 : " + contentType2);
			if (contentType2 != null) {
				image2 = contentType2.startsWith("image/");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return countryStr;
		try {
			mav.addObject("contentType2", contentType2);
			mav.addObject("image2", image2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.setViewName("imageCheck");
		return mav;
	}

}
