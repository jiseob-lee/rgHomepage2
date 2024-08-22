package com.rg.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.login.dto.LoginDTO;
import com.rg.login.service.LoginService;

@Controller
public class LoginController {

	private final Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;

	@RequestMapping("/staff/login.do")
	public String login(Model model) {
		logger.error("Welcome!");
		System.out.println("1234");
		//String now = dao.getTime();
		//model.addAttribute("serverTime", dao.getTime());
		//System.out.println("now : " + now);
		return "staff/login";
	}
	
	@PostMapping("/staff/loginProcess.do")
	public String loginProcess(Model model, LoginDTO loginDTO) {
		logger.error("Welcome3!");
		//System.out.println("1234");
		//String now = dao.getTime();
		//model.addAttribute("serverTime", dao.getTime());
		//System.out.println("now : " + now);
		if (loginDTO != null) {
			logger.error(loginDTO.getUserId());
		}
		return "now";
	}

	@GetMapping("/staff/now1.do")
	public String now(Model model) {
		logger.error("Welcome2!");
		System.out.println("12345");
		//String now = dao.getTime();
		//model.addAttribute("serverTime", dao.getTime());
		//System.out.println("now : " + now);
		return "now";
	}

	@RequestMapping("/loginUser.do")
	@ResponseBody
	public Map<String, String> printUser(ModelMap model) {

		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String name = user.getUsername(); //get logged in username
		//String name = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.error("name : " + name);

		model.addAttribute("username", name);
		
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("username", name);
		
		return m;
	}

}
