package com.rg.downhistory.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.downhistory.service.DownHistoryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DownHistoryController {

	private final Logger logger = LogManager.getLogger(DownHistoryController.class);

	@Autowired
	private DownHistoryService downHistoryService;
 
	@RequestMapping(value = "/rg/getDownHistory.do")
	@ResponseBody
	public Map<String, Object> getDownHistory(HttpServletRequest request) throws IOException {
		
		int pageNo = request.getParameter("pageNo") == null || "".equals(request.getParameter("pageNo")) 
				? 1 : Integer.parseInt(request.getParameter("pageNo"));

		return downHistoryService.getDownHistory(pageNo);
	}
 
}
