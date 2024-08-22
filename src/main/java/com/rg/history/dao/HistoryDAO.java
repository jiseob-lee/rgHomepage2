package com.rg.history.dao;

import java.util.List;

import com.rg.history.dto.HistoryDTO;

public interface HistoryDAO {
	
	public List<HistoryDTO> getHistoryList(HistoryDTO historyDTO);
	
	public int insertHistory(HistoryDTO historyDTO);
	
	public int deleteHistory(int year);
	
}
