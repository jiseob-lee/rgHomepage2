package com.rg.history.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.history.dao.HistoryDAOImpl;
import com.rg.history.dto.HistoryDTO;

@Service("historyService")
public class HistoryServiceImpl implements HistoryService {

	private final Logger logger = LogManager.getLogger(HistoryServiceImpl.class);

	@Autowired
	private HistoryDAOImpl historyDAO;
	

	@Override
	public List<HistoryDTO> getHistoryList(HistoryDTO historyDTO) {
		return historyDAO.getHistoryList(historyDTO);
	}

	@Override
	public int insertHistory(int year, List<HistoryDTO> list) {
		
		logger.debug("");
		
		historyDAO.deleteHistory(year);
		
		int i = 0;
		for (int j = 0; j < list.size(); j++) {
			i += historyDAO.insertHistory(list.get(j));
		}
		return i;
	}
}
