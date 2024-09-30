package com.rg.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

public class GeoLite2 {
	
	public static String getCountry(HttpServletRequest request, String ip) {
		
		String GeoLite2Path = "D:/GeoLite2/GeoLite2-Country.mmdb";
		
		// A File object pointing to your GeoIP2 or GeoLite2 database
		if (new File("/home/ubuntu/GeoLite2").exists()) {
			GeoLite2Path = "/home/ubuntu/GeoLite2/GeoLite2-Country.mmdb";
		}
		
		File database = new File(GeoLite2Path);

		// This creates the DatabaseReader object. To improve performance, reuse
		// the object across lookups. The object is thread-safe.
		DatabaseReader reader = null;
		InetAddress ipAddress = null;
		CountryResponse response = null;
		
		String ip2 = request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For");
		
		try {

			reader = new DatabaseReader.Builder(database).build();
			
			System.out.println("######################################## RemoteAddr 1 : " + request.getRemoteAddr());
			System.out.println("######################################## RemoteAddr 1-1 : " + request.getHeader("X-Forwarded-For"));

			if (ip == null || "".equals(ip)) {
				ipAddress = "127.0.0.1".equals(ip2) || "0:0:0:0:0:0:0:1".equals(ip2)
						? InetAddress.getByName("13.124.60.122") : InetAddress.getByName(ip2);
			} else {
				ipAddress = InetAddress.getByName(ip);
			}
			
			System.out.println("######################################## RemoteAddr 2 : " + ipAddress.toString());
			
			response = reader.country(ipAddress);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AddressNotFoundException e) {
			e.printStackTrace();
			return "KR";
		} catch (GeoIp2Exception e) {
			e.printStackTrace();
		}

		Country country = response.getCountry();
		System.out.println(country.getIsoCode());            // 'US'
		System.out.println(country.getName());               // 'United States'
		System.out.println(country.getNames().get("zh-CN")); // '美国'

		return country.getIsoCode();
	}
	
	@Test
	public void testCountry() {
		
		// A File object pointing to your GeoIP2 or GeoLite2 database
		File database = new File("/home/ubuntu/GeoLite2/GeoLite2-Country.mmdb");
		database = new File("E:/work/20240124 - modal/GeoLite2-Country.mmdb");
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
		System.out.println(country.getIsoCode());            // 'US'
		System.out.println(country.getName());               // 'United States'
		System.out.println(country.getNames().get("zh-CN")); // '美国'
		
	}
}
