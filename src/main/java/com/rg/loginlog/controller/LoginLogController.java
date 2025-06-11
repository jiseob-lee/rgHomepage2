package com.rg.loginlog.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.loginlog.service.LoginLogService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginLogController {

	private final Logger logger = LogManager.getLogger(LoginLogController.class);

	@Autowired
	private LoginLogService loginLogService;
 
	@RequestMapping(value = "/rg/getLoginLog.do")
	@ResponseBody
	public Map<String, Object> getLoginLog(HttpServletRequest request) throws IOException {
		
		int pageNo = request.getParameter("pageNo") == null || "".equals(request.getParameter("pageNo")) 
				? 1 : Integer.parseInt(request.getParameter("pageNo"));

		return loginLogService.getLoginLog(pageNo);
	}
 
}
