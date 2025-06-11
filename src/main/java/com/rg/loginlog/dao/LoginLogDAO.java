package com.rg.loginlog.dao;

import java.util.List;
import java.util.Map;

import com.rg.loginlog.dto.LoginLogDTO;

public interface LoginLogDAO {
	
	public int getLoginLogCount();
	
	public List<LoginLogDTO> getLoginLogList(Map<String, Object> map);
	
}
