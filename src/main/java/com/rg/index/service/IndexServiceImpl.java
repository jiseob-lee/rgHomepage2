package com.rg.index.service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import com.rg.index.dao.IndexDAOImpl;
import com.rg.index.dto.IndexDTO;

@Service("indexService")
public class IndexServiceImpl implements IndexService {

	private final Logger logger = LogManager.getLogger(IndexServiceImpl.class);
	
	@Autowired
	private IndexDAOImpl indexDAO;

	@Override
	public void insertAccessLog(IndexDTO indexDTO) {
		
		try {

			//String GeoLite2Path = "D:/GeoLite2/GeoLite2-Country.mmdb";
			String GeoLite2Path = "D:/GeoLite2/GeoLite2-City.mmdb";
			
			// A File object pointing to your GeoIP2 or GeoLite2 database
			if (new File("/home/ubuntu/GeoLite2").exists()) {
				GeoLite2Path = "/home/ubuntu/GeoLite2/GeoLite2-City.mmdb";
			}
			
			File database = new File(GeoLite2Path);
			
			DatabaseReader reader = new DatabaseReader.Builder(database).build();
			
			InetAddress ipAddress = InetAddress.getByName(indexDTO.getRemoteAddr());

			if ("127.0.0.1".equals(ipAddress.getHostAddress()) || "0:0:0:0:0:0:0:1".equals(ipAddress.getHostAddress())) {
				return;
			}
			
			try {
				// Replace "city" with the appropriate method for your database, e.g.,
				// "country".
				//CountryResponse response = reader.country(ipAddress);
				CityResponse response = reader.city(ipAddress);
	
				Country country = response.getCountry();
				
				logger.debug(country.getIsoCode());
				logger.debug(country.getName());
				
				indexDTO.setCountry(country.getName());
				
				Subdivision subdivision = response.getMostSpecificSubdivision();
				System.out.println(subdivision.getName()); 
				indexDTO.setSubdivision(subdivision.getName());
				
				City city = response.getCity();
				indexDTO.setCity(city.getName());
				
				indexDAO.insertAccessLog(indexDTO);
				
			} catch (AddressNotFoundException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			
			logger.debug("#^##");
			logger.debug("#^########################### Hash : " + indexDTO.getHash());
			logger.debug("#^########################### Referer : " + indexDTO.getReferer());
			logger.debug("#^########################### RemoteAddr : " + indexDTO.getRemoteAddr());
			logger.debug("#^########################### UserAgent : " + indexDTO.getUserAgent());
			logger.debug("#^########################### Language : " + indexDTO.getLanguage());
			logger.debug("#^########################### Country : " + indexDTO.getCountry());
			logger.debug("#^##");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			e.printStackTrace();
		}
	}

}
