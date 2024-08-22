package com.rg.user.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.user.dto.UserDTO;
import com.rg.user.service.UserService;
import com.rg.util.SHA512;

@Controller
public class UserController {

	private final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping("/rg/getUserList.do")
	@ResponseBody
	public List<UserDTO> getUserList(UserDTO userDTO) {
		return userService.getUserList();
	}
	
	@RequestMapping("/rg/insertUser.do")
	@ResponseBody
	public int insertUser(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		String userIdCreated = (String)session.getAttribute("loginId");
		userDTO.setUserIdCreated(userIdCreated);
		
		userDTO.setUserPassword(SHA512.encrypt(userDTO.getUserPassword()));
		
		logger.debug("###################################################");
		logger.debug("userDTO.getUserNameKo() : " + userDTO.getUserNameKo());
		
		return userService.insertUser(userDTO);
	}
	
	@RequestMapping("/rg/updatetUser.do")
	@ResponseBody
	public int updateUser(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		String userIdModified = (String)session.getAttribute("loginId");
		userDTO.setUserIdModified(userIdModified);
		
		logger.debug("################################################");
		logger.debug("####" + userDTO.getUserPassword() + "####");
		logger.debug("################################################");
		
		userDTO.setUserPassword(SHA512.encrypt(userDTO.getUserPassword()));
		
		return userService.updateUser(userDTO);
	}
	
	@RequestMapping("/rg/deleteUser.do")
	@ResponseBody
	public int deleteUser(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String userIdDeleted = (String)session.getAttribute("loginId");
		userDTO.setUserIdDeleted(userIdDeleted);
		
		return userService.deleteUser(userDTO);
	}
}
