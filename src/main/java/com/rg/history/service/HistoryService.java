package com.rg.history.service;

import java.util.List;

import com.rg.history.dto.HistoryDTO;

public interface HistoryService {
	
	public List<HistoryDTO> getHistoryList(HistoryDTO historyDTO);
	
	public int insertHistory(int year, List<HistoryDTO> list);
}
