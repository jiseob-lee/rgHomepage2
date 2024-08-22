package com.rg.manageBoard.service;

import java.util.List;

import com.rg.manageBoard.dto.ManageBoardDTO;

public interface ManageBoardService {
	
	public int getManageBoardListCount(ManageBoardDTO boardDTO);
	
	public List<ManageBoardDTO> getManageBoardList(ManageBoardDTO boardDTO);
	
	public int createBoard(ManageBoardDTO boardDTO);
	
	public int editBoardName(ManageBoardDTO boardDTO);
	
}
