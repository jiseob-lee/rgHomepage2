package com.rg.history.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.history.dto.HistoryDTO;
import com.rg.history.service.HistoryService;

@Controller
public class HistoryController {

	private final Logger logger = LogManager.getLogger(HistoryController.class);
	
	@Autowired
	private HistoryService historyService;
	

	@RequestMapping("/getHistoryList.do")
	@ResponseBody
	public List<HistoryDTO> getHistoryList(HistoryDTO historyDTO) {
		return historyService.getHistoryList(historyDTO);
	}

	@PostMapping("/staff/insertHistory.do")
	@ResponseBody
	public int insertHistory(
			HttpServletRequest request,
			@RequestParam(value = "title", required = false) String[] title,
			@RequestParam(value = "description", required = false) String[] description,
			@RequestParam(value = "order", required = false) String[] order) {

		List<HistoryDTO> list = new ArrayList<HistoryDTO>();
		HistoryDTO dto = null;
		
		if (title == null || title.length == 1) {
			if (request.getParameter("title") != null) {
				dto = new HistoryDTO();
				dto.setYear(Integer.parseInt(request.getParameter("year").trim()));
				dto.setTitle(request.getParameter("title").trim());
				dto.setDescription(request.getParameter("description") == null ? "" : request.getParameter("description").trim());
				dto.setOrder(request.getParameter("order").trim());
				list.add(dto);
			}

		} else if (title != null) {
			for (int i=0; i<title.length; i++) {
				logger.debug("######################## i : " + i);
				dto = new HistoryDTO();
				dto.setYear(Integer.parseInt(request.getParameter("year").trim()));
				dto.setTitle(title[i].trim());
				if (description != null && description.length >= i) {
					logger.debug("description.length : " + description.length);
					logger.debug("description[i] : " + description[i]);
					dto.setDescription(description[i] == null ? "" : description[i].trim());
				}
				dto.setOrder(order[i].trim());
				list.add(dto);
			}
		}
		
		return historyService.insertHistory(Integer.parseInt(request.getParameter("year").trim()), list);
	}
}
