package com.rg.login.dao;

import java.util.Map;

public interface LoginDAO {
	
	public String loginProcess(String userId, String userPw);
	
	public String getUserName(String userId);
	
	public String getLoginPwEncrypted(String userId);
	
	public void putLoginHistory(Map<String, String> loginMap);
}
