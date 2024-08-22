package com.rg.manageBoard.controller;

import java.util.List;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.manageBoard.dto.ManageBoardDTO;
import com.rg.manageBoard.service.ManageBoardService;
import com.rg.util.GeoLite2;
import com.rg.util.LocaleUtil;

@Controller
@CrossOrigin(origins = "http://localhost", maxAge = 3600)
public class ManageBoardController {

	private final Logger logger = LogManager.getLogger(ManageBoardController.class);
	
	@Autowired
	private ManageBoardService manageBoardService;
	

	@RequestMapping("/getManageBoardListCount.do")
	@ResponseBody
	public int getManageBoardListCount(ManageBoardDTO boardDTO) {
		return manageBoardService.getManageBoardListCount(boardDTO);
	}
	
	@RequestMapping("/getManageBoardList.do")
	@ResponseBody
	public List<ManageBoardDTO> getBoardList(ManageBoardDTO boardDTO, HttpServletRequest request, HttpServletResponse response) {
		System.out.println();
		logger.debug("############# OpenYn : " + boardDTO.getOpenYn());
		System.out.println();
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();

		logger.debug("################################### 1 # currentLocale : " + currentLocale);

		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (int i=0; i < cs.length; i++) {
				Cookie c = cs[i];
				logger.debug("###### 1 # currentLocale : " + c.getName() + " : " + c.getValue());
			}
		}
		
		if ("fr".equals(currentLocale)) {
			String country = GeoLite2.getCountry(request, request.getParameter("ip"));
			logger.debug("##################### 9 # country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
			localeUtil.setLocale(currentLocale, request, response);
		}

		//logger.debug("################################### 2 # currentLocale : " + currentLocale);

		
		
		boardDTO.setLocale(currentLocale);
		
		return manageBoardService.getManageBoardList(boardDTO);
	}

	@PostMapping("/rg/createBoard.do")
	@ResponseBody
	public int createBoard(ManageBoardDTO boardDTO, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String userIdCreated = (String)session.getAttribute("loginId");
		boardDTO.setUserIdCreated(userIdCreated);
		
		return manageBoardService.createBoard(boardDTO);
	}
	

	@PostMapping("/rg/editBoardName.do")
	@ResponseBody
	public int editBoardName(ManageBoardDTO boardDTO, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String userIdModified = (String)session.getAttribute("loginId");
		boardDTO.setUserIdModified(userIdModified);
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();

		boardDTO.setLocale(currentLocale);
				
		return manageBoardService.editBoardName(boardDTO);
	}
	
}
