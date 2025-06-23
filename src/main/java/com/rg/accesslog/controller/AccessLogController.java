package com.rg.accesslog.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.accesslog.dto.BoardViewDTO;
import com.rg.accesslog.service.AccessLogService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AccessLogController {
	
	private final Logger logger = LogManager.getLogger(AccessLogController.class);
	

	@Autowired
	private AccessLogService accessLogService;

	@RequestMapping(value= {"/rg/parseBoardView.do"})
	@ResponseBody
	public List<BoardViewDTO> getAttachmentList(HttpServletRequest request, HttpServletResponse response) {
		
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        
		String logDate = request.getParameter("logDate") == null ? formattedDate : request.getParameter("logDate");
		
		List<BoardViewDTO> list = accessLogService.getBoardViewList(logDate);
		
		Collections.reverse(list);
		
		return list;
	}

}
