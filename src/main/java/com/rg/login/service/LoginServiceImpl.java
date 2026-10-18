package com.rg.login.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.login.dao.LoginDAOImpl;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	private final Logger logger = LogManager.getLogger(LoginServiceImpl.class);
	
	private final LoginDAOImpl loginDAO;
	
	public LoginServiceImpl(LoginDAOImpl loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	public String loginProcess(String userId, String userPw) {
		
		logger.debug("");
		
		return loginDAO.loginProcess(userId, userPw);
	}
	
	public String getUserName(String userId) {
		return loginDAO.getUserName(userId);
	}
	
	public String getLoginPwEncrypted(String userId) {
		return loginDAO.getLoginPwEncrypted(userId);
	}
	
	public void putLoginHistory(Map<String, String> loginMap) {
		loginDAO.putLoginHistory(loginMap);
	}
}
