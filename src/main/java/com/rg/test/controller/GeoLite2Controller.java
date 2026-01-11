package com.rg.test.controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.util.GeoLite2;

@Controller
public class GeoLite2Controller {
	
	private final Logger logger = LogManager.getLogger(GeoLite2Controller.class);
	
	@RequestMapping("/rg/getCountry.do")
	@ResponseBody
	public Map<String, String> getCountry(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		Map<String, String> geoLiteMap = GeoLite2.getIpInfo(request);
		
		map.put("country", geoLiteMap == null ? "" : geoLiteMap.get("country"));

		return map;
	}

}
