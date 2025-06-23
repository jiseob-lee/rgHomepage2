package com.rg.accesslog.service;

import java.util.List;

import com.rg.accesslog.dto.BoardViewDTO;

public interface AccessLogService {
	
	public List<BoardViewDTO> getBoardViewList(String logDate);
	
}
