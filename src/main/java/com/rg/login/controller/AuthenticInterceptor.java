package com.rg.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.rg.login.dto.UserDetailsVO;
import com.rg.util.RedisService3;

public class AuthenticInterceptor implements HandlerInterceptor {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisService3 redisService;

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	HttpSession session = request.getSession();
    	String sId = session.getId();
    	String loginId = (String)session.getAttribute("loginId");
    	String redisKey = "LOGIN||SESSION||" + loginId + "||" + sId;
    	
    	UserDetailsVO loginVO = redisService.selectRedisSession(redisKey);
    	
    	if (loginVO == null || loginVO.getLoginId() == null || "".equals(loginVO.getLoginId()) || "null".equals(loginVO.getLoginId())) {
    		
    		response.sendRedirect("/logout");
    		
    		return false;
    	}
    	
    	return true;
    }
}
