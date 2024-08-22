package com.rg2.mssqlsp.controller;

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

import com.rg2.mssqlsp.service.MssqlSPService;

@Controller
public class MssqlSPController {

	private final Logger logger = LogManager.getLogger(MssqlSPController.class);

	@Autowired
	private MssqlSPService mssqlSPService;

	@RequestMapping(value = "/rg/spTest.do")
	public String spTest(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		logger.info("..........................................");
		mssqlSPService.getTable1(param);
		logger.info(param);
		return "sp";
	}
}
