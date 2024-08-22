package com.rg.test.controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.test.dto.RequestHostDTO;

@Controller
public class RequestHostController {

	private final Logger logger = LogManager.getLogger(RequestHostController.class);

	@RequestMapping("/rg/requestHost.do")
	@ResponseBody
	public Map<String, String> requestHost(RequestHostDTO requestHostDTO,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("requestHost", requestHostDTO.getRequestHost());
		logger.debug("######################### requestHostDTO.getRequestHost : " + requestHostDTO.getRequestHost());
		logger.debug("######################### request.getParameter(requestHost) : " + request.getParameter("requestHost"));
		return map;
	}
}
