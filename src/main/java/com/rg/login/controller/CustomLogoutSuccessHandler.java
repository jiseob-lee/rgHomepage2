package com.rg.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.rg.util.CookieHandle;
import com.rg.util.RedisService3;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private RedisService3 redisService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		/*
		if (authentication != null && authentication.getDetails() != null) {
			try {
				request.getSession().invalidate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/
		
		HttpSession session = request.getSession(false);
		
		String loginId = CookieHandle.getCookie(request, "login_id");
		String sessionId = CookieHandle.getCookie(request, "session_id");
		
		redisService.processRedisLogout("LOGIN||SESSION||" + loginId + "||" + sessionId);
		
		if (session != null) {
			session.invalidate();
		}
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect("/");
	}
}