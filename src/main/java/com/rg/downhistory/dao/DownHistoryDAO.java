package com.rg.downhistory.dao;

import java.util.List;
import java.util.Map;

import com.rg.loginlog.dto.LoginLogDTO;

public interface DownHistoryDAO {
	
	public int getDownHistoryCount();
	
	public List<LoginLogDTO> getDownHistoryList(Map<String, Object> map);
	
}
