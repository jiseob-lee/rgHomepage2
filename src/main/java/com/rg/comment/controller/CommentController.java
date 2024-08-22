package com.rg.comment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;

import com.rg.comment.dto.CommentDTO;
import com.rg.comment.service.CommentService;
import com.rg.util.LocaleUtil;
import com.rg.util.SHA512;

@Controller
public class CommentController {

	private final Logger logger = LogManager.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	
	//public static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");
	
	
	@RequestMapping("/getCommentList.do")
	@ResponseBody
	public List<CommentDTO> getCommentList(CommentDTO commentDTO) {
		
		//ModelAndView mav = new ModelAndView();
		//mav.setVi
		//List<CommentDTO> list = commentService.getCommentList(commentDTO);
	
		//CommentDTO commentDTO = new CommentDTO();
		//commentDTO.setBoardArticleIdx(Integer.valueOf(articleNo).toString());

		LocaleUtil localeUtil = new LocaleUtil();
		String currentLocale = localeUtil.getLocale().getLanguage();
		
		commentDTO.setLocale(currentLocale);
		commentDTO.setOpenYn("Y");
		
		List<CommentDTO> commentList = commentService.getCommentList(commentDTO);
		
		return commentList;
	}
	
	
	@RequestMapping(value="/updateComment.do", method=RequestMethod.POST)
	@ResponseBody
	public void updateComment(CommentDTO commentDTO, HttpServletRequest request) {
		commentDTO.setUserPassword(SHA512.encrypt(commentDTO.getUserPassword()));
		commentService.updateComment(commentDTO, request);
	}

	@RequestMapping(value="/deleteComment.do")
	@ResponseBody
	public void deleteComment(HttpServletRequest request, CommentDTO commentDTO) {

		/*
		CsrfToken token = (CsrfToken)request.getAttribute("_csrf");
		
		logger.debug("###################### csrfToken (at delete 1) : " + token.getToken());
		
		CsrfToken sessionToken = (CsrfToken) request.getSession().getAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME);
		
		logger.debug("###################### csrfToken (at delete 2) : " + sessionToken.getToken());
		*/
		
		commentDTO.setUserPassword(SHA512.encrypt(commentDTO.getUserPassword()));
		
		Integer checkCount = commentService.checkCommentPassword(commentDTO);
		
		if (checkCount == 1) {
			;
		} else {
			
		}
		
		
		commentService.deleteComment(commentDTO);
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/inputComment.do", method = RequestMethod.OPTIONS)
	public ResponseEntity options(HttpServletResponse response) {
	    logger.info("OPTIONS /foo called");
	    response.setHeader("Allow", "HEAD,GET,PUT,OPTIONS");
	    return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/inputComment.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> inputComment(CommentDTO commentDTO, HttpServletRequest request) {
		
		logger.debug("#################################### commentDTO.getContent : " + commentDTO.getContent());
		logger.debug("#################################### request.getParameter-content : " + request.getParameter("content"));
		
		commentDTO.setUserPassword(SHA512.encrypt(commentDTO.getUserPassword()));
		
		commentService.inputComment(commentDTO, request);
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "true");
		return map;
	}
	
	@RequestMapping(value="/checkCommentPassword.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkCommentPassword(HttpServletRequest request, CommentDTO commentDTO) {
		
		//CsrfToken token = (CsrfToken)request.getAttribute("_csrf");
		
		//logger.debug("###################### csrfToken (after password check) : " + token.getToken());
		if (!"55".equals(commentDTO.getCommentIdx()) && !"54".equals(commentDTO.getCommentIdx())) {
			commentDTO.setUserPassword(SHA512.encrypt(commentDTO.getUserPassword()));
		}
		
		logger.debug("###################### UserPassword : " + commentDTO.getUserPassword());
		logger.debug("###################### CommentIdx : " + commentDTO.getCommentIdx());
		
		
		Map<String, Object> map = new HashMap<String, Object>();
				
		Integer checkCount = commentService.checkCommentPassword(commentDTO);
		
		map.put("checkCount", checkCount);
		
		
		logger.debug("###################### checkCount : " + checkCount);

		logger.debug("###################### Action : " + commentDTO.getAction());
		
		
		if (checkCount == 0) {  // 비밀번호가 일치하지 않음.
			
			return map;
			
		} else {  // 비밀번호가 일치함.
			
			if ("update".equals(commentDTO.getAction())) {  // 댓글 수정
				commentService.updateComment(commentDTO, request);
				
			} else if ("delete".equals(commentDTO.getAction())) {  // 댓글 삭제
				commentService.deleteComment(commentDTO);
			}
		}
		
		//map.put("csrfToken", token.getToken());
		
		return map;
	}

	/*
	@RequestMapping(value="/checkCsrfToken1.do", method=RequestMethod.GET)
	public ModelAndView checkCsrfToken1(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("csrfToken1");
		
		CsrfToken token = (CsrfToken)request.getAttribute("_csrf");
		
		logger.debug("###################### csrfToken (check) : " + token.getToken());
		
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("checkCount", checkCount);
		map.put("csrfToken", token.getToken());
		
		mav.addObject("csrfToken", token.getToken());
		
		return mav;
	}

	@RequestMapping(value="/checkCsrfToken2.do", method=RequestMethod.GET)
	public ModelAndView checkCsrfToken2(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("csrfToken2");
		
		CsrfToken token = (CsrfToken)request.getAttribute("_csrf");
		
		logger.debug("###################### csrfToken (check) : " + token.getToken());
		
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("checkCount", checkCount);
		map.put("csrfToken", token.getToken());
		
		mav.addObject("csrfToken", token.getToken());
		
		return mav;
	}
	*/
	
}
