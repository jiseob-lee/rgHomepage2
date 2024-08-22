package com.rg.test;

import jakarta.inject.Inject;

import org.apache.logging.log4j.LogManager;
//import org.apache.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeController {
	
	private final Logger logger = LogManager.getLogger(TimeController.class);
	
	@Inject
	private TimeDAOImpl dao;
	
	@GetMapping("/now.do")
	public String now(Model model) {
		logger.error("Welcome!");
		System.out.println("1234");
		String now = dao.getTime();
		model.addAttribute("serverTime", dao.getTime());
		System.out.println("now : " + now);
		return "now";
	}
	
}
