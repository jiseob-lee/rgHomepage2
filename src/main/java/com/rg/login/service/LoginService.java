package com.rg.login.service;

public interface LoginService {
	
	public String loginProcess(String userId, String userPw);
	
	public String getUserName(String userId);
	
	public String getLoginPwEncrypted(String userId);
}
