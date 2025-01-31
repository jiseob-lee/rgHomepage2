package com.rg.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

import jakarta.servlet.http.HttpServletRequest;

public class GeoLite2 {
	
	private static final Logger logger = LogManager.getLogger(GeoLite2.class);
	
	public static String getCountry(HttpServletRequest request, String ip) {
		
		Country country = null;
		
		String GeoLite2Path = "E:/GeoLite2/GeoLite2-City.mmdb";
		
		// A File object pointing to your GeoIP2 or GeoLite2 database
		if (new File("/home/ubuntu/GeoLite2").exists()) {
			GeoLite2Path = "/home/ubuntu/GeoLite2/GeoLite2-City.mmdb";
		}
		
		File database = new File(GeoLite2Path);

		// This creates the DatabaseReader object. To improve performance, reuse
		// the object across lookups. The object is thread-safe.
		DatabaseReader reader = null;
		InetAddress ipAddress = null;
		CityResponse response = null;
		
		String ip2 = request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For");
		
		try {

			reader = new DatabaseReader.Builder(database).withCache(new CHMCache()).build();
			
			//WebServiceClient client = new WebServiceClient.Builder(171734, "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")
				    //.build();
			
			//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
			logger.debug("######################################## RemoteAddr 1 : " + request.getRemoteAddr());
			logger.debug("######################################## RemoteAddr 1-1 : " + request.getHeader("X-Forwarded-For"));

			if (ip == null || "".equals(ip)) {
				ipAddress = "127.0.0.1".equals(ip2) || "0:0:0:0:0:0:0:1".equals(ip2)
						? InetAddress.getByName("13.124.60.122") : InetAddress.getByName(ip2);
			} else {
				ipAddress = InetAddress.getByName(ip);
			}
			
			logger.debug("######################################## RemoteAddr 2 : " + ipAddress.toString());
			
			response = reader.city(ipAddress);
			//response = client.city(ipAddress);
			
			country = response.getCountry();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AddressNotFoundException e) {
			e.printStackTrace();
			return "KR";
		} catch (GeoIp2Exception e) {
			e.printStackTrace();
		}

		//Country country = response.getCountry();
		logger.debug(country.getIsoCode());            // 'US'
		logger.debug(country.getName());               // 'United States'
		logger.debug("중국어 : " + country.getNames().get("zh-CN")); // '美国'
		//logger.debug(country.getNames().get("ko-KR"));
		//java.util.Locale locale1 = new java.util.Locale("ko", result.getCountryCode());
		//logger.debug("국가명 : " + locale1.getDisplayCountry());
		
		return country.getIsoCode();
	}
	
	@Test
	public void testCountry() {
		
		// A File object pointing to your GeoIP2 or GeoLite2 database
		File database = new File("/home/ubuntu/GeoLite2/GeoLite2-Country.mmdb");
		//database = new File("E:/work/20240124 - modal/GeoLite2-Country.mmdb");
		database = new File("E:/GeoLite2/GeoLite2-Country.mmdb");
		// This creates the DatabaseReader object. To improve performance, reuse
		// the object across lookups. The object is thread-safe.
		DatabaseReader reader = null;
		InetAddress ipAddress = null;
		CountryResponse response = null;

		try {

			reader = new DatabaseReader.Builder(database).build();
			
			ipAddress = InetAddress.getByName("13.124.111.141");

			response = reader.country(ipAddress);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			e.printStackTrace();
		}

		Country country = response.getCountry();
		logger.debug(country.getIsoCode());            // 'US'
		logger.debug(country.getName());               // 'United States'
		logger.debug(country.getNames().get("zh-CN")); // '美国'
		
	}
}
