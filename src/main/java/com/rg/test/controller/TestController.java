package com.rg.test.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.login.controller.CustomLoginSuccessHandler;

@Controller
public class TestController {

	private final Logger logger = LogManager.getLogger(TestController.class);

	@Autowired
	CustomLoginSuccessHandler loginSuccessHandler;
	
	@RequestMapping("/rg/setSession.do")
	@ResponseBody
	public Boolean setSession(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		UUID uuid = UUID.randomUUID();
		
		//session.setAttribute(request.getParameter("sessionName"), request.getParameter("sessionValue"));
		session.setAttribute("uuid", uuid.toString());

		session.setAttribute("getUuidCount", 0);
		
		//Cookie cookie = new Cookie(request.getParameter("sessionName"), request.getParameter("sessionValue"));
		//cookie.setPath("/");
		//cookie.setHttpOnly(true);
		//cookie.setSecure(true);
		//response.addCookie(cookie);
		
		return true;
	}

	//@RequestMapping("/rg/setCookie.do")
	//@ResponseBody
	//public Boolean setCookie(HttpServletRequest request, HttpServletResponse response) {
		//HttpSession session = request.getSession();
		//session.setAttribute(request.getParameter("sessionName"), request.getParameter("sessionValue"));

		//return true;
	//}

	@RequestMapping("/rg/getUuid.do")
	@ResponseBody
	public Map<String, String> getUuid(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		Integer getUuidCount = (Integer)session.getAttribute("getUuidCount");
		
		if (getUuidCount == null) {
			getUuidCount = 1;
		}
		
		logger.info("getUuidCount : " + getUuidCount);
		
		session.setAttribute("getUuidCount", getUuidCount + 1);
		
		//String sessionValue = (String)session.getAttribute(request.getParameter("sessionName"));

		//Cookie[] cookies = request.getCookies();
		//String cookieValue = "";
		//if (cookies != null) {
			//for (Cookie cookie : cookies) {
				//if (cookie.getName().equals(request.getParameter("sessionName"))) {
					//cookieValue = cookie.getValue();
					//cookie.isHttpOnly();
					//logger.info("cookie " + request.getParameter("sessionName") + " value is " + cookieValue);
				//}
			//}
		//}

		Map<String, String> map = new HashMap<>();
		//if (sessionValue != null && sessionValue.equals(cookieValue)) {
		if (getUuidCount == 0) {
			//UUID uuid = UUID.randomUUID();
			//map.put("uuid", uuid.toString());
			map.put("uuid", (String)session.getAttribute("uuid"));
		}

		return map;
	}

	@RequestMapping("/forceLogin_rg.do")
	//@ResponseBody
	public String /*Map<String, String>*/ forceLogin(HttpServletRequest request, HttpServletResponse response) {

		/*
		List<GrantedAuthority> authorities = new ArrayList<>();
		//authorities.add(new SimpleGrantedAuthority(""));

		authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
		authorities.add(new SimpleGrantedAuthority("ROLE_DBA"));
    	
		//Authentication authentication = new UsernamePasswordAuthenticationToken("rg", "jiseob9123", authorities);

		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken("rg", "jiseob9123", authorities);
		result.setDetails(new CustomUserDetails("rg", "jiseob9123"));
        
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(result);

		HttpSession session = request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);   // 세션에 spring security context 넣음

		try {
			loginSuccessHandler.onAuthenticationSuccess(request, response, result);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		*/
		try {
			response.sendRedirect("/forceLogin_rg2.do");
			//return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;
		return "test/test";
	}

	@RequestMapping("/forceLogin_rg2.do")
	//@ResponseBody
	public String /*Map<String, String>*/ forceLogin2(HttpServletRequest request, HttpServletResponse response) {
		return "test/test2";
	}

}
