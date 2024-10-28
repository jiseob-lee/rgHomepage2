package com.rg.login.dao;

public interface LoginDAO {
	
	public String loginProcess(String userId, String userPw);
	
	public String getUserName(String userId);
	
	public String getLoginPwEncrypted(String userId);
	
}
