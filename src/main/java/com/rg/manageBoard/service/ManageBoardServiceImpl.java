package com.rg.manageBoard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.manageBoard.dto.ManageBoardDTO;
import com.rg.manageBoard.dao.ManageBoardDAOImpl;

@Service("manageBoardService")
public class ManageBoardServiceImpl implements ManageBoardService {

	private final Logger logger = LogManager.getLogger(ManageBoardServiceImpl.class);

	@Autowired
	private ManageBoardDAOImpl manageBoardDAO;
	

	@Override
	public int getManageBoardListCount(ManageBoardDTO boardDTO) {
		return manageBoardDAO.getManageBoardListCount(boardDTO);
	}

	@Override
	public List<ManageBoardDTO> getManageBoardList(ManageBoardDTO boardDTO) {
		return manageBoardDAO.getManageBoardList(boardDTO);
	}

	@Override
	public int createBoard(ManageBoardDTO boardDTO) {
		boardDTO = manageBoardDAO.createBoard(boardDTO);
		return boardDTO.getBoardIdx();
	}

	@Override
	public int editBoardName(ManageBoardDTO boardDTO) {
		return manageBoardDAO.editBoardName(boardDTO);
	}

}
