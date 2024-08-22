package com.rg.manageBoard.dao;

import java.util.List;

import com.rg.manageBoard.dto.ManageBoardDTO;

public interface ManageBoardDAO {
	
	public int getManageBoardListCount(ManageBoardDTO boardDTO);
	
	public List<ManageBoardDTO> getManageBoardList(ManageBoardDTO boardDTO);
	
	//public ManageBoardDTO getBoardContent(ManageBoardDTO boardDTO);

	public ManageBoardDTO createBoard(ManageBoardDTO boardDTO);

	public int editBoardName(ManageBoardDTO boardDTO);
	
	//public int updateBoardArticle(ManageBoardDTO boardDTO);
	
	//public int deleteBoardArticle(ManageBoardDTO boardDTO);
	
	//public void increaseHitCount(ManageBoardDTO boardDTO);
	
	//public void updateAttachmentBoardArticleIdx(Map<String, Object> map);
}
