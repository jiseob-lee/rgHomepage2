package com.rg.slowCityMoviePlay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.slowCityMoviePlay.service.SlowCityMoviePlayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SlowCityMoviePlayController {

	private final Logger logger = LogManager.getLogger(SlowCityMoviePlayController.class);

	@Autowired
	private SlowCityMoviePlayService slowCityMoviePlayService;

	@RequestMapping("/registSlowCityMoviePlay.do")
	@ResponseBody
	public int registSlowCityMoviePlay(HttpServletRequest request, HttpServletResponse response) {
		
		return slowCityMoviePlayService.registSlowCityMoviePlay(request);
		
	}
	
}
