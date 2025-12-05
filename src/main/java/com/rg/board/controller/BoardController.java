package com.rg.board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.board.dto.BoardDTO;
import com.rg.board.service.BoardService;
import com.rg.index.dto.IndexDTO;
import com.rg.index.service.IndexService;
import com.rg.util.GeoLite2;
import com.rg.util.LocaleUtil;

@Controller
@CrossOrigin(origins = "http://localhost", maxAge = 3600)
public class BoardController {

	private final Logger logger = LogManager.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;

	@Autowired
	private IndexService indexService;

	static private String ip = "";
	
	static {
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(
                				whatismyip.openStream()));
			ip = in.readLine();
			System.out.println("######################## machine ip : " + ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getBoardListCount.do")
	@ResponseBody
	public int getBoardListCount(BoardDTO boardDTO, HttpServletRequest request) {
		//logger.debug("########## /getBoardListCount.do");
		
		//CustomUserDetails userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
		HttpSession session = request.getSession();
		
		String loginId = (String)session.getAttribute("loginId");
		
		if (loginId != null && loginId.equals("rg111")) {
			logger.info("# 3 #### openYn : n");
			boardDTO.setOpenYn("n");
		} else {
			logger.info("# 3 #### openYn : y");
			boardDTO.setOpenYn("y");
		}
		
		//boardDTO.setOpenYn("y");
		return boardService.getBoardListCount(boardDTO);
	}
	
	@RequestMapping("/getBoardList.do")
	@ResponseBody
	public List<BoardDTO> getBoardList(BoardDTO boardDTO, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("########## /getBoardList.do");
		
		//CustomUserDetails userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
		HttpSession session = request.getSession();
		
		String loginId = (String)session.getAttribute("loginId");
		
		//logger.debug("########## /getBoardList.do");
		
		
		//logger.debug("########## /getBoardList.do boardDTO.getLocale 3 : " + boardDTO.getLocale());
		
		//Locale locale = LocaleContextHolder.getLocale();
		//logger.debug("########## /getBoardList.do boardDTO.getLocale 2 : " + locale.getLanguage());
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		/*
		if ("fr".equals(currentLocale)) {
			String country = GeoLite2.getCountry(request);
			logger.debug("###################### country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
			localeUtil.setLocale(currentLocale, request, response);
		}
		LocaleUtil localeUtil = new LocaleUtil();
		*/
		boardDTO.setLocale(currentLocale);
		//logger.debug("########## /getBoardList.do boardDTO.getLocale : " + boardDTO.getLocale());
		//localeUtil.setLocale(language, request, response);
		
		logger.info("# 3 #### loginId : " + loginId);
		
		if (loginId != null && loginId.equals("rg111")) {
			logger.info("# 3 #### openYn : n");
			boardDTO.setOpenYn("n");
		} else {
			logger.info("# 3 #### openYn : y");
			boardDTO.setOpenYn("y");
		}
		
		return boardService.getBoardList(boardDTO);
	}
	
	@RequestMapping(value = "/getBoardContent.do", method = RequestMethod.GET)
	@ResponseBody
	public BoardDTO getBoardContent(BoardDTO boardDTO, HttpServletRequest request, HttpServletResponse response) {
		
		//CustomUserDetails userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
		HttpSession session = request.getSession();
		
		String loginId = (String)session.getAttribute("loginId");
		
		logger.debug("#^##");
		logger.debug("#^########################## RequestURI : " + request.getRequestURI());
		logger.debug("#^########################## RequestURL : " + request.getRequestURL().toString());
		logger.debug("#^########################## ContextPath : " + request.getContextPath());
		logger.debug("#^########################## QueryString : " + request.getQueryString());
		logger.debug("#^########################## boardArticleIdx : " + request.getParameter("boardArticleIdx"));
		logger.debug("#^########################## pathname : " + request.getParameter("pathname"));
		logger.debug("#^########################## referrer : " + request.getParameter("referrer"));
		logger.debug("#^########################## lang : " + request.getParameter("lang"));
		logger.debug("#^########################## User-Agent : " + request.getHeader("User-Agent"));
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		logger.debug("#^########################## currentLocale 1 : " + currentLocale);
		
		if ("fr".equals(currentLocale)) {
			String country = GeoLite2.getCountry(request, request.getParameter("ip"));
			logger.debug("##################### 1 # country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
			localeUtil.setLocale(currentLocale, request, response);
		}
		
		logger.debug("#^########################## currentLocale 2 : " + currentLocale);
		logger.debug("#^##");
		
		String referer = request.getHeader("referer");
		logger.debug("########################## referer 1 : " + referer);
		referer = request.getParameter("referrer");
		logger.debug("########################## referer 2 : " + referer);
		
		logger.info("#### getBoardContent. 6");
		
		String ip2 = request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For");
		
		logger.info("#### ip2 : " + ip2 + ", ip : " + ip);
		
		if (request.getSession() != null && request.getSession().getAttribute("loginId") == null && !ip.equals(ip2)) {

			IndexDTO indexDTO = new IndexDTO();
			indexDTO.setProxyClientIp(request.getHeader("Proxy-Client-IP"));
			indexDTO.setRemoteAddr(ip2);
			indexDTO.setUserAgent(request.getHeader("User-Agent"));
			indexDTO.setxForwardedFor(request.getHeader("X-FORWARDED-FOR"));
			indexDTO.setHash(request.getParameter("pathname"));
			indexDTO.setReferer(referer);
			
			indexDTO.setLanguage(currentLocale);
			
			logger.info("#### getBoardContent. 5");
			
			indexService.insertAccessLog(indexDTO);

			logger.info("#### getBoardContent. 4");
			
			// 조회수 증가
			boardService.increaseHitCount(boardDTO);
		}

		logger.info("#### getBoardContent. 3");
		
		//logger.debug("########## /getBoardContent.do");
		//LocaleUtil localeUtil = new LocaleUtil();
		//localeUtil.getLocale();
		//localeUtil.setLocale(language, request, response);
		boardDTO.setLocale(currentLocale);
		
		logger.info("# 3 #### loginId : " + loginId);
		
		if (loginId != null && loginId.equals("rg")) {
			boardDTO.setOpenYn("n");
		} else {
			boardDTO.setOpenYn("y");
		}
		
		logger.info("#### getBoardContent. 2");
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		// Spring Security will allow the Token to be included in this header name
		response.setHeader("X-CSRF-HEADER", token == null ? "" : token.getHeaderName());
		// Spring Security will allow the token to be included in this parameter name
		response.setHeader("X-CSRF-PARAM", token == null ? "" : token.getParameterName());
		// this is the value of the token to be included as either a header or an HTTP parameter
		response.setHeader("X-CSRF-TOKEN", token == null ? "" : token.getToken());

		//Cookie setCookie = new Cookie("XSRF-TOKEN", token.getToken()); // 쿠키 이름을 name으로 생성
		//setCookie.setMaxAge(60*60*24); // 기간을 하루로 지정
		//setCookie.setPath("/");
		//setCookie.setDomain(".jisblee.me");
		//setCookie.setSecure(false);
		//response.addCookie(setCookie);
		
		logger.info("#### getBoardContent. 1");
		
		//return boardService.getBoardContent(boardDTO);
		
		boardDTO.setRequestURI(request.getRequestURI());
		
		BoardDTO returnDTO = boardService.getBoardContent(boardDTO);
		
		//returnDTO.setCsrfToken(token.getToken());
		
		return returnDTO;
	}

	@RequestMapping("/rg/getBoardListCount.do")
	@ResponseBody
	public int getBoardListCountAdmin(BoardDTO boardDTO) {
		//logger.debug("########## /rg/getBoardListCount.do");
		return boardService.getBoardListCountRg(boardDTO);
	}
	
	@RequestMapping("/rg/getBoardList.do")
	@ResponseBody
	public List<BoardDTO> getBoardListAdmin(BoardDTO boardDTO) {
		logger.debug("########## /rg/getBoardList.do");
		return boardService.getBoardListRg(boardDTO);
	}

	@RequestMapping("/rg/getBoardContent.do")
	@ResponseBody
	public BoardDTO getBoardContentAdmin(BoardDTO boardDTO, HttpServletRequest request) {
		logger.debug("########## /rg/getBoardContent.do");
		boardDTO.setRequestURI(request.getRequestURI());
		return boardService.getBoardContent(boardDTO);
	}

	
	@RequestMapping("/increaseHitCount.do")
	@ResponseBody
	public void increaseHitCount(BoardDTO boardDTO, HttpServletRequest request) {
		//logger.debug("########## /increaseHitCount.do");
		HttpSession session = request.getSession();
		if (session.getAttribute("loginId") == null) {
			boardService.increaseHitCount(boardDTO);
		}
	}
	
	@PostMapping("/rg/insertBoardArticle.do")
	@ResponseBody
	public int insertBoardArticle(BoardDTO boardDTO, 
			@RequestParam(value = "attachmentIdxs[]", required = false) Integer[] attachmentIdxs,
			HttpServletRequest request) {

		//logger.debug("########## /rg/insertBoardArticle.do");
		
		HttpSession session = request.getSession();
		String userIdCreated = (String)session.getAttribute("loginId");
		boardDTO.setUserIdCreated(userIdCreated);

		//logger.debug("###################################################");
		//logger.debug("BoardIdx : " + boardDTO.getBoardIdx());
		//logger.debug("Subject : " + boardDTO.getSubject());
		//logger.debug("Content : " + boardDTO.getContent());
		//logger.debug("Content : " + request.getParameter("content"));
		//logger.debug("BoardArticleIdx : " + boardDTO.getBoardArticleIdx());
		//logger.debug("UserIdCreated : " + boardDTO.getUserIdCreated());
		//logger.debug("###################################################");
		
		
		return boardService.insertBoardArticle(boardDTO, attachmentIdxs);
		

		/*
		logger.debug("########################## 0 #########################");
		
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
		    String param = e.nextElement();
		    System.out.println(param + " : " + request.getParameter(param));
		}
		
		logger.debug("########################## 1 #########################");
		logger.debug("########################## 2 #########################");
		logger.debug(request.getParameter("content"));
		logger.debug("########################## 3 #########################");
		logger.debug("########################## 4 #########################");
		
		return 1;
		*/
	}

	@PostMapping("/rg/updateBoardArticle.do")
	@ResponseBody
	public int updateBoardArticle(BoardDTO boardDTO, 
			@RequestParam(value = "attachmentIdxs[]", required = false) Integer[] attachmentIdxs, 
			HttpServletRequest request) {
		
		//logger.debug("########## /rg/updateBoardArticle.do");
		
		//logger.debug("###################################################");
		//logger.debug("Subject : " + boardDTO.getSubject());
		//logger.debug("Content : " + boardDTO.getContent());
		//logger.debug("BoardArticleIdx : " + boardDTO.getBoardArticleIdx());
		//logger.debug("UserIdModified : " + boardDTO.getUserIdModified());
		//logger.debug("###################################################");
		
		return boardService.updateBoardArticle(boardDTO, attachmentIdxs);
	}

	@PostMapping("/rg/deleteBoardArticle.do")
	@ResponseBody
	public int deleteBoardArticle(BoardDTO boardDTO) {
		
		//logger.debug("########## /rg/deleteBoardArticle.do");
		
		//logger.debug("#####################################################");
		//logger.debug("BoardArticleIdx : " + boardDTO.getBoardArticleIdx());
		//logger.debug("UserIdModified : " + boardDTO.getUserIdModified());
		return boardService.deleteBoardArticle(boardDTO);
	}
	
}
