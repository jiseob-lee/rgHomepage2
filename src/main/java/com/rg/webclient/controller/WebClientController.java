package com.rg.webclient.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.webclient.dto.ExternalRequest;
import com.rg.webclient.service.WebClientService;

import reactor.core.publisher.Mono;

@Controller
public class WebClientController {
	
	private final Logger logger = LogManager.getLogger(WebClientController.class);
	
	private final WebClientService webClientService;

	public WebClientController(WebClientService webClientService) {
		this.webClientService = webClientService;
	}
	
	@RequestMapping(value = "/createGet.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public Mono<String> createGet() {
		
		Mono<String> value = webClientService.createGet();
		
		logger.info("#### value (get) : " + value);
		
		return value;
	}

	@RequestMapping(value = "/createPost.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public Mono<String> createPost(@RequestParam Map<String, String> request) {
		
		logger.info("#### request : " + request.toString());
		
		Mono<String> value = webClientService.createPost(request);
		
		logger.info("#### value (post) : " + value);
		
		return value;
	}

	@RequestMapping(value = "/createPost2.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public Mono<String> createPost2(@RequestParam Map<String, String> allRequestParams) {
		
		Mono<String> value = webClientService.createPost2(allRequestParams);
		
		logger.info("#### value (post2) : " + value);
		
		return value;
	}

	@RequestMapping(value = "/createResponse.do")
	@ResponseBody
	public Map<String, String> createResponse(ExternalRequest request) {
		
		Map<String, String> map = new HashMap<>();
		
		map.put("resultCode", "OK");
		//map.put("resultCode", "NotOK");
		map.put("message", "Error");
		map.put("data", "1234");
		
		logger.info("#### map : " + map.toString());
		
		return map;
	}
}
