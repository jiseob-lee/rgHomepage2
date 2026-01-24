package com.rg.webclient.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.webclient.service.WebClientService;

@Controller
public class WebClientController {
	
	private final Logger logger = LogManager.getLogger(WebClientController.class);
	
	@Autowired
	private WebClientService webClientService;
	
	@RequestMapping(value = "/createPost.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String createPost() {
		
		String value = webClientService.createPost();
		
		logger.info("#### value : " + value);
		
		return value;
	}
}
