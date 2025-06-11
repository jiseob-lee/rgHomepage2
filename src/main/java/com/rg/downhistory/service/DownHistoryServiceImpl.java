package com.rg.downhistory.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.downhistory.dao.DownHistoryDAOImpl;
import com.rg.loginlog.dto.LoginLogDTO;

@Service("downHistoryService")
public class DownHistoryServiceImpl implements DownHistoryService {

	private final Logger logger = LogManager.getLogger(DownHistoryServiceImpl.class);
	
	@Autowired
	private DownHistoryDAOImpl downHistoryDAO;

	@Override
	public Map<String, Object> getDownHistory(int pageNo) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("pageNo", pageNo);
		
		int listLimit = 10;
		
		map.put("listLimit", listLimit);
		
		int totalCount = downHistoryDAO.getDownHistoryCount();
		
		map.put("totalCount", totalCount);
		
		map.put("skipCount", (pageNo - 1) * listLimit);
		
		List<LoginLogDTO> list = downHistoryDAO.getDownHistoryList(map);
		
		map.put("list", list);
		
		return map;
	}
	
}
