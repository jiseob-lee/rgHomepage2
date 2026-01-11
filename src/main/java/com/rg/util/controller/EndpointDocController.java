package com.rg.util.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/rg")
public class EndpointDocController {
	
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping(value = {"/endPoints.do", "/endPoints3.do"}, method = RequestMethod.GET)
	public String getEndPointsInView(Model model, HttpServletRequest request) {

		
		Instant start = Instant.now();
		
		System.out.println("#### RequestURI : " + request.getRequestURI());
		System.out.println("#### RequestURL : " + request.getRequestURL());

		List<String> endPointsList = new ArrayList<>();
		
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		
		
		model.addAttribute("map", map);
		
		for (int j=0; j < 40; j++) {
			map = requestMappingHandlerMapping.getHandlerMethods();
			
			map.entrySet().forEach(entry -> {
				////LOG.info("Key : " + entry.getKey() + " Value : " + entry.getValue());
				////LOG.info(entry.getKey().getPatternsCondition().toString());
				
				String pattern = entry.getKey().getPathPatternsCondition().toString();
				pattern = pattern.substring(1, pattern.length() - 1);
				if (pattern.endsWith("endPoints3.do")) {
					PathPatternsRequestCondition condition = entry.getKey().getPathPatternsCondition().getMatchingCondition(request);
					if (condition != null) {
						//LOG.info("#### condition : " + condition.toString());
						System.out.println("#### condition : " + condition.toString());
					}
				}
				/*
				pattern = pattern.substring(1, pattern.length() - 1);
				if (pattern.indexOf(" || ") > -1) {
					String[] patternArray = pattern.split(" \\|\\| ");
					for (int i=0; i < patternArray.length; i++) {
						endPointsList.add(patternArray[i]);
					}
				} else {
					endPointsList.add(pattern);
				}
				*/
			});
		}

		Instant finish = Instant.now();
		
		long timeElapsed = Duration.between(start, finish).toMillis();

		System.out.println("#### timeElapsed : " + timeElapsed);
		System.out.println("#### endPointsList unit size : " + endPointsList.size() / 40);
		System.out.println("#### endPointsList total size : " + endPointsList.size());
				
		model.addAttribute("endPointsList", endPointsList);
		
		return "tools/endPoints";
	}


	@RequestMapping(value = "/endPoints2", method = RequestMethod.GET)
	public String getEndPointsInView2(Model model, HttpServletRequest request) {

		return "tools/endPoints";
	}

}
