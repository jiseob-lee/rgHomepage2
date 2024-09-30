package com.rg.util.controller;

//import java.io.UnsupportedEncodingException;
import java.util.HashMap;
//import java.util.Locale;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.LocaleResolver;

import com.rg.login.dto.CustomUserDetails;
import com.rg.login.dto.UserDetailsVO;
import com.rg.login.service.LoginService;
import com.rg.util.GeoLite2;
import com.rg.util.LocaleUtil;
import com.rg.util.RedisService3;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost", maxAge = 3600)
public class EnvironmentController {

	private final Logger logger = LogManager.getLogger(EnvironmentController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private RedisService3 redisService;
	
	//@Autowired
	//private LocaleResolver localeResolver;

	//@RequestMapping(value = "/getEnvironment.do")
	@RequestMapping(value = {"/getEnvironment.do", "/{lang}/getEnvironment.do"})
	@ResponseBody
	public Map<String, String> getEnvironment(@PathVariable("lang") Optional<String> langVal,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();

		UserDetailsVO vo = null;
		
		//try {
			//request.setCharacterEncoding("UTF-8");
		//} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		//}
		//https://gmlwjd9405.github.io/2019/01/01/spring-utf8.html
		
		CustomUserDetails userInfo = null;
		
		String loginId = null;
		String loginUserName = null;
		
		try {
			if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof CustomUserDetails) {
					userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
					loginId = userInfo.getUsername();
					loginUserName = loginService.getUserName(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String redisKey = "LOGIN||SESSION||" + loginId + "||" + session.getId();
		
		vo = redisService.selectRedisSession(redisKey);

		//if (vo == null) {
			//return null;
		//}
				
		loginId = vo == null ? "" : vo.getLoginId();
		
		//loginId = (String)session.getAttribute("loginId");
		
		String ip2 = request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For");
		
		logger.info("#### remoteAddr : " + ip2);
		logger.info("#### loginId : " + loginId);
		logger.info("#### loginUserName : " + loginUserName);
		
		Map<String, String> map = new HashMap<String, String>();
		
		//HttpSession session = request.getSession();
		if (vo != null && vo.getLoginId() != null && !"".equals(vo.getLoginId()) && !"null".equals(vo.getLoginId())) {
			map.put("loginId", vo.getLoginId());
			map.put("loginUserName", vo.getLoginUserName());
			
			int sessionTime = 60 * 60 * 5;
			redisService.setTimeOutSecond(redisKey, sessionTime);
		}
		//map.put("loginId", loginId);
		//map.put("loginUserName", loginUserName);

		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		if (langVal.isPresent()) {
			currentLocale  = langVal.get();
			
		//} else if ("fr".equals(currentLocale)) {
		} else {
			String country = GeoLite2.getCountry(request, request.getParameter("ip"));
			//logger.debug("###################### country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
		}
		
		localeUtil.setLocale(currentLocale, request, response);

		//logger.debug("############## log 1 : " + currentLocale);
		//logger.debug("############## log 2 : " + request.getParameter("ip"));
		//logger.debug("############## log 2 : " + request.getParameter("lang"));

		String lang = "";
		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (int i=0; i < cs.length; i++) {
				Cookie c = cs[i];
				//logger.debug("######### log 2 : " + i + " : " + c.getName() + " : " + c.getValue());
				if ("lang".equals(c.getName())) {
					lang = c.getValue();
				}
			}
		}
		
		//logger.debug("############## log 3 : " + request.getParameter("lang"));
		
		//logger.debug("############## log 4 : " + request.getRequestURL().toString() + "?" + request.getQueryString());
		
		
		/*
		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (int i=0; i < cs.length; i++) {
				Cookie c = cs[i];
				logger.debug("###### 1 # currentLocale : " + c.getName() + " : " + c.getValue());
				if ("JSESSIONID".equals(c.getName()) && c.getValue() != null && !"".equals(c.getValue())) {
					map.put("JSESSIONID", c.getValue());
				}
			}
		}
		*/
		
		//localeResolver.setLocale(request, response, new Locale(currentLocale));

		
		if (lang != null && !"".equals(lang)) {
			map.put("locale", lang);
		} else {
			map.put("locale", currentLocale);
		}
		
		//logger.debug("###################### loginId : " + map.get("loginId"));
		//logger.debug("###################### loginUserName : " + map.get("loginUserName"));
		//logger.debug("###################### currentLocale : " + currentLocale);
		
		//String referrer = request.getHeader("referer");
		
		//map.put("referrer", referrer);

		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		map.put("csrfToken", token == null ? "" : token.getToken());
		
		logger.info("#### map : " + map);
		
		return map;
	}

	//public void setLocaleResolver(LocaleResolver localeResolver) {
		//this.localeResolver = localeResolver;
	//}

	@RequestMapping(value = {"/getEmptyRequest.do", "/getEmptyRequest1.do", "/getEmptyRequest2.do", "/getEmptyRequest3.do", "/getEmptyRequest4.do"})
	@ResponseBody
	public Map<String, String> getEmptyRequest(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("######################## RequestURI : " + request.getRequestURI());
		logger.debug("######################## RequestURL : " + request.getRequestURL().toString());
		Map<String, String> map = new HashMap<String, String>();
		map.put("RequestURI", request.getRequestURI());
		return map;
	}
}
