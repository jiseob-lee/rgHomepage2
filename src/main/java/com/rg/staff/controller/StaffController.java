package com.rg.staff.controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.login.service.LoginService;

@Controller
public class StaffController {

	private final Logger logger = LogManager.getLogger(StaffController.class);
	
	@Autowired
	private LoginService loginService;

	
	@RequestMapping("/rg/index2.do")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		if (session.getAttribute("loginId") == null) {
			
			String id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			logger.error("id : " + id);
			
			String userName = loginService.getUserName(id);
			
			if (userName == null || "".equals(userName)) {
				return "redirect:/login.jsp";
			}
			
			session.setAttribute("loginId", id);
			session.setAttribute("loginUserName", userName);

			
			//Cookie myCookie1 = new Cookie("loginId", id);
			//response.addCookie(myCookie1);
			
			//Cookie myCookie2 = new Cookie("loginUserName", userName);
			//response.addCookie(myCookie2);
			
		}
		
		return "rg/index";
	}

	/*
	@RequestMapping("/staff/menu.do")
	public String menu(Model model) {
		return "staff/menu";
	}

	@RequestMapping("/staff/itnews.do")
	public String itnews(Model model) {
		return "staff/itnews";
	}

	@RequestMapping("/staff/notice.do")
	public String notice(Model model) {
		return "staff/notice";
	}
	*/

	@RequestMapping("/rg/getLoginUserInfo2.do")
	@ResponseBody
	public Map<String, String> getLoginUserName(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginId", (String)session.getAttribute("loginId"));
		map.put("loginUserName", (String)session.getAttribute("loginUserName"));
		return map;
	}

}
