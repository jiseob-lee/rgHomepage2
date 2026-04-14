package com.rg.loginlog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.loginlog.dao.LoginLogDAOImpl;
import com.rg.loginlog.dto.LoginLogDTO;

@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {

	private final Logger logger = LogManager.getLogger(LoginLogServiceImpl.class);
	
	@Autowired
	private LoginLogDAOImpl loginLogDAO;

	@Override
	public Map<String, Object> getLoginLog(int pageNo) {
		
		logger.debug("");
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("pageNo", pageNo);
		
		int listLimit = 10;
		
		map.put("listLimit", listLimit);
		
		int totalCount = loginLogDAO.getLoginLogCount();
		
		map.put("totalCount", totalCount);
		
		map.put("skipCount", (pageNo - 1) * listLimit);
		
		List<LoginLogDTO> list = loginLogDAO.getLoginLogList(map);
		
		map.put("list", list);
		
		return map;
	}
	
}
