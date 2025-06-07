package com.rg.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.rg.login.service.LoginService;
import com.rg.util.GeoLite2;
import com.rg.util.IP;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginFailHandler implements AuthenticationFailureHandler {
	
	private final Logger logger = LogManager.getLogger(CustomLoginFailHandler.class);
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException accessException) throws IOException, ServletException {
		
		logger.error("Access Denied Handler");
		logger.error("Redirect....");
		
		String userId = request.getParameter("username");
		//출처: https://baessi.tistory.com/80 [배씨의 프로그래밍:티스토리]
		
		logger.info("#### userId : " + request.getParameter("username"));

		
		
		
		// 로그인 이력 기록
		
		Map<String, String> loginMap = new HashMap<>();
		
		loginMap.put("loginId", userId);
		loginMap.put("loginResult", "fail");
		loginMap.put("ip", IP.getClientIP(request));
		
		Map<String, String> ipInfo = GeoLite2.getIpInfo(request);
		loginMap.put("country", ipInfo.get("country"));
		loginMap.put("subdivision", ipInfo.get("subdivision"));
		loginMap.put("city", ipInfo.get("city"));
		
		loginService.putLoginHistory(loginMap);
		
		
		
		if (accessException instanceof AuthenticationServiceException) {
			request.setAttribute("error", "존재하지 않는 사용자입니다.");
		
		} else if(accessException instanceof BadCredentialsException) {
			request.setAttribute("error", "비밀번호가 틀립니다.");
			
		} else if(accessException instanceof LockedException) {
			request.setAttribute("error", "잠긴 계정입니다..");
			
		} else if(accessException instanceof DisabledException) {
			request.setAttribute("error", "비활성화된 계정입니다..");
			
		} else if(accessException instanceof AccountExpiredException) {
			request.setAttribute("error", "만료된 계정입니다..");
			
		} else if(accessException instanceof CredentialsExpiredException) {
			request.setAttribute("error", "비밀번호가 만료되었습니다.");
		}
		
		// 로그인 페이지로 다시 포워딩
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.do?error=true");
		dispatcher.forward(request, response);
		
	}

}
