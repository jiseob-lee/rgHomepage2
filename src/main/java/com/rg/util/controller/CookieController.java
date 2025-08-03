package com.rg.util.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.util.CookieHandle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CookieController {

	private final Logger logger = LogManager.getLogger(CookieController.class);
	
	@RequestMapping("/api/setCookie.do")
	public void setCookie(HttpServletRequest request, HttpServletResponse response) {
		CookieHandle.setCookieHttpOnly(response, "testName", "testValue", -1);
	}

	@RequestMapping("/api/getCookie.do")
	@CrossOrigin(origins = "https://jisblee.me:8443", allowCredentials = "true", methods = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCookie(HttpServletRequest request, HttpServletResponse response, 
			@CookieValue("testName") String testName1) {
		
		Map<String, Object> map = new HashMap<>();
		
		String testName = CookieHandle.getCookie(request, "testName");
	    
		//String value = Arrays.stream(request.getCookies())
                //.filter(c -> c.getName().equals("testName"))
                //.findFirst()
                //.map(Cookie::getValue)
                //.orElse("없음");
		
		//logger.info("#### value : " + value);
		
		logger.info("#### testName : " + testName);
		
		logger.info("#### testName1 : " + testName1);
		
		map.put("testName1", testName1);
		
		return map;
	}
}
