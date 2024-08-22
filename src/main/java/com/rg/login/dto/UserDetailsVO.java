package com.rg.login.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserDetailsVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3978021319183008639L;
	
	private String loginUserId1;
	private String loginId;
	private String loginUserName;
	
	public String getLoginUserId1() {
		return loginUserId1;
	}
	public void setLoginUserId1(String loginUserId1) {
		this.loginUserId1 = loginUserId1;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}


	public Map<String, String> getMap() {
		Map<String, String> map = new HashMap<>();

		map.put("loginUserId1", loginUserId1);
		map.put("loginId", loginId);
		map.put("loginUserName", loginUserName);

		return map;
	}
}
