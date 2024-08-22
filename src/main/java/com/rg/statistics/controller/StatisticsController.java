package com.rg.statistics.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.statistics.dto.ArticleCountDTO;
import com.rg.statistics.dto.LogDTO;
import com.rg.statistics.service.StatisticsService;

@Controller
public class StatisticsController {

	private final Logger logger = LogManager.getLogger(StatisticsController.class);

	@Autowired
	private StatisticsService statisticsService;
 
	@RequestMapping(value = "/rg/getIpCount.do")
	@ResponseBody
	public Map<String, Object> getIpCount(HttpServletRequest request) throws IOException {
		
		int pageNo = request.getParameter("pageNo") == null || "".equals(request.getParameter("pageNo")) 
				? 1 : Integer.parseInt(request.getParameter("pageNo"));

		return statisticsService.getIpCount(pageNo);
	}
 
	@RequestMapping(value = "/rg/getStatisticsArticleSubjects.do")
	@ResponseBody
	public List<ArticleCountDTO> getStatisticsArticleSubjects(HttpServletRequest request) throws IOException {

		Map<String, String> map = new HashMap<String, String>();
		
		map.put("fromDate", request.getParameter("fromDate"));
		map.put("toDate", request.getParameter("toDate"));
		
		return statisticsService.getStatisticsArticleSubjects(map);
	}

	@RequestMapping(value = "/rg/getStatisticsLogs.do")
	@ResponseBody
	public List<LogDTO> getStatisticsLogs(HttpServletRequest request) throws IOException {

		if (request.getParameter("myDate") == null || "".equals(request.getParameter("myDate"))) {
			return null;
		}

		return statisticsService.getStatisticsLog(request.getParameter("myDate"));
	}

}
