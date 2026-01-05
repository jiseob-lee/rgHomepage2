package com.rg.index.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rg.attachment.dto.AttachmentDTO;
import com.rg.attachment.service.AttachmentService;
import com.rg.board.dto.BoardDTO;
import com.rg.board.service.BoardService;
import com.rg.comment.dto.CommentDTO;
import com.rg.comment.service.CommentService;
import com.rg.index.dto.IndexDTO;
import com.rg.index.service.IndexService;
import com.rg.login.controller.CustomLoginSuccessHandler;
import com.rg.login.dto.CustomUserDetails;
import com.rg.login.dto.UserDetailsVO;
import com.rg.login.service.LoginService;
import com.rg.manageBoard.dto.ManageBoardDTO;
import com.rg.manageBoard.service.ManageBoardService;
import com.rg.util.AES256Util;
import com.rg.util.CookieHandle;
import com.rg.util.GeoLite2;
import com.rg.util.IP;
import com.rg.util.LocaleUtil;
import com.rg.util.RedisService3;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

	private final Logger logger = LogManager.getLogger(IndexController.class);

	@Autowired
	private IndexService indexService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ManageBoardService manageBoardService;

	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	CustomLoginSuccessHandler loginSuccessHandler;

	@Autowired
	private RedisService3 redisService;
		
	private int listLimit = 10;  // listLimit
	private int pageLinkCount = 10;  // pageLinkCount

	@RequestMapping(value = "/login.do")
	public String login(HttpServletResponse response, HttpServletRequest request) {

		logger.debug("#### login.do 1.");
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		if ("fr".equals(currentLocale)) {
			String country = GeoLite2.getCountry(request, request.getParameter("ip"));
			logger.debug("##################### 2 # country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
			localeUtil.setLocale(currentLocale, request, response);
		}

		logger.debug("#### login.do 2.");
		
		
		AES256Util aes256Util = new AES256Util();
		
		String loginIdEnc = null;//getCookie(request, "loginId");
		String loginTokenDec = null;
		String loginIdDec = null;
		String loginPwDec = null;
		
		Cookie[] cookies = request.getCookies();

	    if (cookies != null) {
	        for (Cookie c : cookies) {
	            String name = c.getName(); // 쿠키 이름 가져오기
	            String value = c.getValue(); // 쿠키 값 가져오기
	            if (name.equals("loginId")) {
	            	loginIdEnc = value;
	            }
	        }
	    }
	    
	    logger.debug("#### login.do 3.");
	    
		if (loginIdEnc != null) {
			
			try {
				loginTokenDec = aes256Util.decrypt(loginIdEnc);
				logger.info("#### loginTokenDec : " + loginTokenDec);
				String[] tmpArr = loginTokenDec.split("\\|\\|");
				loginIdDec = "".equals(loginTokenDec) ? "" : tmpArr[0];
				loginPwDec = tmpArr.length > 1 ? tmpArr[1] : "";
			} catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
			}

			logger.debug("#### login.do 4.");
			
			if ("yoon".equals(loginIdDec) || "rg".equals(loginIdDec)) {

				String loginPwEncryptrdInDB = loginService.getLoginPwEncrypted(loginIdDec);
				
				if (!loginPwDec.equals(loginPwEncryptrdInDB)) {
					return "login";
				}
				
				Cookie cookie = new Cookie("loginId", loginIdEnc); // 쿠키 이름 지정하여 생성( key, value 개념)
			    cookie.setMaxAge(60*60*24*100); //쿠키 유효 기간: 하루로 설정(60초 * 60분 * 24시간)
			    cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
			    cookie.setHttpOnly(true);
			    cookie.setSecure(true);
			    
			    response.addCookie(cookie); //response에 Cookie 추가
			    
				List<GrantedAuthority> authorities = new ArrayList<>();
				//authorities.add(new SimpleGrantedAuthority(""));

	            if ("rg".equals(loginIdDec)) {
	            	authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
	            	authorities.add(new SimpleGrantedAuthority("ROLE_DBA"));
	            } else if ("yoon".equals(loginIdDec)) {
	            	authorities.add(new SimpleGrantedAuthority("ROLE_MOM"));
	            }
	            
	            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

				//Authentication authentication = new UsernamePasswordAuthenticationToken("rg", "jiseob9123", authorities);
	            UsernamePasswordAuthenticationToken result = null;
	            
	            logger.debug("#### login.do 5.");
	            
	            if ("rg".equals(loginIdDec)) {
		            result = new UsernamePasswordAuthenticationToken("rg", "jiseob9123", authorities);
					result.setDetails(new CustomUserDetails("rg", "jiseob9123"));
	            } else if ("yoon".equals(loginIdDec)) {
	            	result = new UsernamePasswordAuthenticationToken("yoon", "3025458", authorities);
					result.setDetails(new CustomUserDetails("yoon", "3025458"));
	            }
				SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(result);
				
				logger.debug("#### login.do 6.");
				
				HttpSession session = request.getSession(true);
				session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);   // 세션에 spring security context 넣음
				
				logger.debug("#### login.do 7.");
				
				try {
					loginSuccessHandler.onAuthenticationSuccess(request, response, result);
				} catch (IOException | ServletException e) {
					e.printStackTrace();
				}
				
			    //return "redirect:/rg";
				
				logger.debug("#### login.do 8.");
				
			}
		}

		return "login";
	}

	private void setCookie(HttpServletResponse res, String cookieName, String value) {
	    Cookie cookie = new Cookie(cookieName, value); // 쿠키 이름 지정하여 생성( key, value 개념)
	    //cookie.setMaxAge(60*60*24*100); //쿠키 유효 기간: 하루로 설정(60초 * 60분 * 24시간)
	    cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
	    //cookie.setDomain(".jisblee.me");
	    res.addCookie(cookie); //response에 Cookie 추가
	}
	
	private String getCookie(HttpServletRequest req, String cookieName) {
		try {
	    Cookie[] cookies = req.getCookies(); // 모든 쿠키 가져오기
	    if (cookies != null) {
	        for (Cookie c : cookies) {
	            String name = c.getName(); // 쿠키 이름 가져오기
	            String value = c.getValue(); // 쿠키 값 가져오기
	            if (name.equals(cookieName)) {
	                return value;
	            }
	        }
	    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}

	

	@RequestMapping(value = "/")
	public ModelAndView indexSlash(HttpServletResponse response, HttpServletRequest request) {
		return new ModelAndView("redirect:/board/list/0/1");
	}

	
	@RequestMapping(value = {"/index.do", "/board/list/{boardNo}/{pageNo}"})
	public ModelAndView index(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("boardNo") Optional<Integer> boardNoVal, 
			@PathVariable("pageNo") Optional<Integer> pageNoVal) {
		
		logger.debug(request.getServerName());
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		if ("fr".equals(currentLocale)) {
			String country = GeoLite2.getCountry(request, request.getParameter("ip"));
			logger.debug("##################### 3 # country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
			localeUtil.setLocale(currentLocale, request, response);
		}
		
		ModelAndView mav = new ModelAndView();
		

		CustomUserDetails userInfo = null;
		
		String loginId = null;
		//String loginUserName = null;
		
		try {
			if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof CustomUserDetails) {
					userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
					loginId = userInfo.getUsername();
					//loginUserName = loginService.getUserName(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		if (loginId == null) {
			logger.info("################################ session set null #####################################");
			session.setAttribute("loginId", null);
			session.setAttribute("loginUserName", null);
		}
		
		mav.addObject("loginId", loginId);
		
		mav.addObject("currentLocale", currentLocale);
		
		mav.setViewName("index");

		//HttpSession session = request.getSession();
		//mav.addObject("loginId", session.getAttribute("loginId"));
		
		//System.out.println();
		//logger.debug("1------------------------------------------------- session loginId : " + session.getAttribute("loginId"));
		//System.out.println();
		
		//if (!request.getServerName().startsWith("www")) {
			//return mav;
		//}
		ManageBoardDTO manageBoardDTO = new ManageBoardDTO();
		manageBoardDTO.setOpenYn("1");
		manageBoardDTO.setLocale(currentLocale);
		
		int manageBoardListCount = manageBoardService.getManageBoardListCount(manageBoardDTO);
		
		//boardDTO.setListLimit(0);
		//boardDTO.setListOffset(boardListCount);
		
		List<ManageBoardDTO> manageBoardList = manageBoardService.getManageBoardList(manageBoardDTO);
		
		//logger.debug("############ boardList : " + manageBoardList);
		
		mav.addObject("manageBoardListCount", manageBoardListCount);
		mav.addObject("manageBoardList", manageBoardList);

		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setOpenYn("y");
		boardDTO.setLocale(currentLocale);
		
		
		int pageNo = 1;
		if (pageNoVal.isPresent()) {
			pageNo = pageNoVal.get();  // pageNo
		}

		int boardNo = 0;
		if (boardNoVal.isPresent()) {
			boardNo = boardNoVal.get();  // pageNo
		}
		boardDTO.setBoardIdx(boardNo);
		
		//int boardNo = 0;  // boardNo
		
		int boardListCount = boardService.getBoardListCount(boardDTO);
		
		boardDTO.setListLimit(listLimit);
		boardDTO.setListOffset(listLimit * (pageNo - 1));
		
		List<BoardDTO> boardList1 = boardService.getBoardList(boardDTO);
		List<BoardDTO> boardList = new ArrayList<BoardDTO>(); 
		
		for (int i=0; i<boardList1.size(); i++) {
			BoardDTO boardItem = boardList1.get(i);
			if ("en".equals(boardDTO.getLocale())) {
				boardItem.setSubject(boardItem.getSubject() + "<br/>" + boardItem.getSubjectEng());
			}
			for (int j=0; j<manageBoardList.size(); j++) {
				ManageBoardDTO manageBoardItem = manageBoardList.get(j);
				if (boardItem.getBoardIdx() == manageBoardItem.getBoardIdx()) {
					boardItem.setBoardName(manageBoardItem.getBoardName());
					boardList.add(boardItem);
					break;
				}
			}
		}
		
		//logger.debug("############ boardList : " + boardList);
		
		mav.addObject("boardListCount", boardListCount);  // boardListCount
		mav.addObject("boardList", boardList);  // boardList


		//board.boardListCount = (response.data == null || response.data == "") ? 0 : response.data;

		//$scope.boardListCount = board.boardListCount;
		int pageEnd = Double.valueOf(Math.ceil(Integer.valueOf(boardListCount).doubleValue() / Integer.valueOf(listLimit).doubleValue())).intValue();  // 마지막 페이지 번호
		//board.pageEnd = $scope.pageEnd;
		
		if (pageNo > pageEnd) {
			pageNo = pageEnd;
		}

		//$scope.pageNo = board.pageNo;
		//$scope.listLimit = board.listLimit;  // 한 페이지 게시글 수
		//$scope.pageLinkCount = board.pageLinkCount; // 페이지 이동 링크 개수
		
		int linkStart = Double.valueOf(Math.floor((pageNo - 1) / pageLinkCount) * pageLinkCount + 1).intValue();  // linkStart
		int linkEnd = (pageEnd - linkStart > pageLinkCount) ? linkStart + pageLinkCount : pageEnd;
		
		//logger.debug("####### boardListCount : " + boardListCount);
		//logger.debug("####### listLimit : " + listLimit);
		//logger.debug("####### boardListCount / listLimit : " + Math.ceil(Integer.valueOf(boardListCount).doubleValue() / Integer.valueOf(listLimit).doubleValue()));
		//logger.debug("####### pageEnd : " + pageEnd);
		//logger.debug("####### linkStart : " + linkStart);
		//logger.debug("####### linkEnd : " + linkEnd);
		//logger.debug("####### pageLinkCount : " + pageLinkCount);
		
		//$scope.linkStart = board.linkStart;
		//$scope.linkEnd = board.linkEnd;
		
		mav.addObject("listLimit", listLimit);
		mav.addObject("pageNo", pageNo);
		mav.addObject("boardNo", boardNo);
		mav.addObject("pageLinkCount", pageLinkCount);
		mav.addObject("linkStart", linkStart);
		mav.addObject("linkEnd", linkEnd);

				
		//boardNo
		return mav;
	}
	
	//https://www.jisblee.me/board/list/0/2
	@RequestMapping("/board1/list/{boardNo}/{pageNo}")
	public void boardList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("boardNo") int boardNo, @PathVariable("pageNo") int pageNo) {
		//logger.debug("###################################### boardNo :" + boardNo);
		//logger.debug("###################################### pageNo :" + pageNo);
		
	}
	
	@RequestMapping("/board/view1/{boardNo}/{pageNo}/{articleNo}")
	public ModelAndView boardView(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("boardNo") int boardNo, 
			@PathVariable("pageNo") int pageNo, 
			@PathVariable("articleNo") int articleNo) {
		

		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		if ("fr".equals(currentLocale)) {
			String country = GeoLite2.getCountry(request, request.getParameter("ip"));
			logger.debug("##################### 4 # country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
			localeUtil.setLocale(currentLocale, request, response);
		}
		
		
		
		
		//CsrfToken token = (CsrfToken)request.getAttribute("_csrf");
		
		//logger.debug("###################### csrfToken (at board view) : " + token.getToken());
	
		
		//logger.debug("###################################### boardNo :" + boardNo);
		//logger.debug("###################################### pageNo :" + pageNo);
		//logger.debug("###################################### articleNo :" + articleNo);
		
		String referer = request.getHeader("referer");
		
		//logger.debug("###################################### referer :" + referer);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("currentLocale", currentLocale);
		
		mav.setViewName("boardView");
		

		
		ManageBoardDTO manageBoardDTO = new ManageBoardDTO();
		manageBoardDTO.setOpenYn("1");
		manageBoardDTO.setLocale(currentLocale);
		
		int manageBoardListCount = manageBoardService.getManageBoardListCount(manageBoardDTO);
		
		//boardDTO.setListLimit(0);
		//boardDTO.setListOffset(boardListCount);
		
		List<ManageBoardDTO> manageBoardList = manageBoardService.getManageBoardList(manageBoardDTO);
		
		//logger.debug("############ boardList : " + manageBoardList);
		
		mav.addObject("manageBoardListCount", manageBoardListCount);
		mav.addObject("manageBoardList", manageBoardList);

		
		
		BoardDTO boardDTO = new BoardDTO();
		
		boardDTO.setBoardArticleIdx(articleNo);
		boardDTO.setOpenYn("y");
		boardDTO.setLocale(currentLocale);
		
		
		BoardDTO boardContent = boardService.getBoardContent(boardDTO);
		
		mav.addObject("boardContent", boardContent);
		
		
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		attachmentDTO.setBoardArticleIdx(Integer.valueOf(articleNo).toString());
		attachmentDTO.setBoardIdx(Integer.valueOf(boardNo).toString());
		List<AttachmentDTO> attachmentList = attachmentService.getAttachmentList(attachmentDTO);
		
		mav.addObject("attachmentList", attachmentList);
		
		//mav.addObject("pageNo", pageNo);
		//mav.addObject("boardNo", boardNo);
		
		
		
		String ip2 = request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For");
		
		if (request.getSession().getAttribute("loginId") == null) {
			
			IndexDTO indexDTO = new IndexDTO();
			indexDTO.setProxyClientIp(request.getHeader("Proxy-Client-IP"));
			indexDTO.setRemoteAddr(ip2);
			indexDTO.setUserAgent(request.getHeader("User-Agent"));
			indexDTO.setxForwardedFor(request.getHeader("X-FORWARDED-FOR"));
			
			indexDTO.setHash(request.getRequestURI().substring(request.getContextPath().length()));
			indexDTO.setReferer(referer);
			
			indexDTO.setLanguage(currentLocale);
						
			indexService.insertAccessLog(indexDTO);

			// 조회수 증가
			boardService.increaseHitCount(boardDTO);
		}


		//HttpSession session = request.getSession();
		//mav.addObject("loginId", session.getAttribute("loginId"));
		
		
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setBoardArticleIdx(Integer.valueOf(articleNo).toString());
		commentDTO.setLocale(currentLocale);
		
		List<CommentDTO> commentList = commentService.getCommentList(commentDTO);
		
		//if (commentList != null) {
			//logger.debug("################## commentList.size : " + commentList.size());
		//} else {
			//logger.debug("################## commentList.size : 0");
		//}
		
		mav.addObject("commentList", commentList);
		
		return mav;
	}
	
	@RequestMapping("/logHash.do")
	public void logHash(HttpServletRequest request, HttpServletResponse response) {
		
		String ip2 = request.getHeader("X-Forwarded-For") == null ? request.getRemoteAddr() : request.getHeader("X-Forwarded-For");
		
		if (request.getSession().getAttribute("loginId") == null) {
			
			IndexDTO indexDTO = new IndexDTO();
			indexDTO.setProxyClientIp(request.getHeader("Proxy-Client-IP"));
			indexDTO.setRemoteAddr(ip2);
			indexDTO.setUserAgent(request.getHeader("User-Agent"));
			indexDTO.setxForwardedFor(request.getHeader("X-FORWARDED-FOR"));
			
			//StringBuffer requestURL = request.getRequestURL();
			//if (request.getQueryString() != null) {
			    //requestURL.append("?").append(request.getQueryString());
			//}
			//String completeURL = requestURL.toString();
			//String hash = request.getParameter("hash");
			//if (hash != null) {
				//requestURL.append(hash);
			//}
			indexDTO.setHash(request.getParameter("hash"));
			
			//System.out.println("##### " + request.getParameter("hash"));
			
			indexService.insertAccessLog(indexDTO);
		}
		
	}

	
	@RequestMapping(value = {"/rg/index.do", "/rg/"})
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();

		//logger.debug("###################### locale (/rg/index.do) : " + currentLocale);
		
		if ("fr".equals(currentLocale)) {
			String country = GeoLite2.getCountry(request, request.getParameter("ip"));
			logger.debug("##################### 5 # country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
			//localeUtil.setLocale(currentLocale, request, response);
		}
		
		logger.debug("################################## /rg/index.do Current locale : " + currentLocale);
		
		localeUtil.setLocale(currentLocale, request, response);
		
		mav.addObject("currentLocale", currentLocale);
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("loginId") == null) {
			
			String id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			//logger.error("id : " + id);
			
			String userName = loginService.getUserName(id);
			
			if (userName == null || "".equals(userName)) {
				//return "redirect:/login.jsp";
				mav.setViewName("redirect:/login.jsp");
				return mav;
			}
			
			session.setAttribute("loginId", id);
			session.setAttribute("redisLoginId", "^" + id);
			logger.error("###################### redisLoginId : " + "^" + id);
			session.setAttribute("loginUserName", userName);

			
			//Cookie myCookie1 = new Cookie("loginId", id);
			//response.addCookie(myCookie1);
			
			//Cookie myCookie2 = new Cookie("loginUserName", userName);
			//response.addCookie(myCookie2);
			
		}
		
		//System.out.println();
		//logger.debug("2------------------------------------------------- session loginId : " + session.getAttribute("loginId"));
		//System.out.println();
		
		//return "rg/index";
		mav.setViewName("rg/index_20210909");
		return mav;
	}

	@RequestMapping(value= {"/rg/getLoginUserInfo.do", "/getLoginUserInfo.do"})
	@ResponseBody
	public Map<String, String> getLoginUserName(Model model, HttpServletRequest request) {

		CustomUserDetails userInfo = null;
		
		String loginId = null;
		String loginUserName = null;
		
		try {
			if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				logger.info("################## SecurityContextHolder.getContext().getAuthentication().isAuthenticated()");
				if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof CustomUserDetails) {
					userInfo = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
					loginId = userInfo.getUsername();
					loginUserName = loginService.getUserName(loginId);
					logger.info("##################################### SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof CustomUserDetails");
				} else if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
					logger.info("##################################### instanceof UsernamePasswordAuthenticationToken");
				} else if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
					logger.info("##################################### instanceof WebAuthenticationDetails");
					
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			        //UserDetails principal = (UserDetails) auth.getPrincipal();
			        System.out.println("[principal] : " + auth.getPrincipal());
			        //System.out.println("  username : " + principal.getUsername());
			        //System.out.println("  password : " + principal.getPassword());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		HttpSession session = request.getSession();
		
		UserDetailsVO vo = redisService.selectRedisSession("LOGIN||SESSION||" + loginId + "||" + session.getId());
		
		//Map<String, String> map = new HashMap<String, String>();
		//loginId = (String)session.getAttribute("loginUserId1");
		logger.info("################################ loginId : " + (String)session.getAttribute("loginUserId1"));
		
		if (vo == null || vo.getLoginId() == null || "".equals(vo.getLoginId()) || "null".equals(vo.getLoginId())) {
			session.removeAttribute("loginId");
			session.removeAttribute("loginUserName");

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(null);
			SecurityContextHolder.clearContext();
			
			return null;
			
		} else {
			session.setAttribute("loginId", vo.getLoginId());
			session.setAttribute("loginUserName", vo.getLoginUserName());
			
			return vo.getMap();
		}
		//map.put("loginId", loginId);
		//map.put("loginUserName", loginUserName);
		//map.put("loginId", "rg");
		//map.put("loginUserName", "loginUserName");
		//return map;
		
		//return vo == null ? null : vo.getMap();
	}

	@RequestMapping("/rg/getLocale.do")
	@ResponseBody
	public Map<String, String> getLocale(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		logger.debug("###################### Current locale 1 (/rg/getLocale.do) : " + currentLocale);
		
		if ("fr".equals(currentLocale)) {
			String country = GeoLite2.getCountry(request, request.getParameter("ip"));
			logger.debug("##################### 6 # country : " + country);
			if ("KR".equals(country)) {
				currentLocale = "ko";
			} else {
				currentLocale = "en";
			}
			//localeUtil.setLocale(currentLocale, request, response);
		}
		
		localeUtil.setLocale(currentLocale, request, response);
		setCookie(response, "org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", currentLocale);

		logger.debug("###################### Current locale 2 (/rg/getLocale.do) : " + currentLocale);
		
		map.put("locale", currentLocale);
		
		return map;
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {

		logger.info("#### logout called....");
		
		
		HttpSession session = request.getSession(false);
		
		String loginId = CookieHandle.getCookie(request, "login_id");
		String sessionId = CookieHandle.getCookie(request, "session_id");
		
		redisService.processRedisLogout("LOGIN||SESSION||" + loginId + "||" + sessionId);
		
		if (session != null) {
			session.invalidate();
		}
		
		return "logout";
	}
}
