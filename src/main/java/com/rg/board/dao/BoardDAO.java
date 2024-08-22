package com.rg.board.dao;

import java.util.List;
import java.util.Map;

import com.rg.board.dto.BoardDTO;

public interface BoardDAO {
	
	public int getBoardListCount(BoardDTO boardDTO);
	
	public int getBoardListCountRg(BoardDTO boardDTO);
	
	public List<BoardDTO> getBoardList(BoardDTO boardDTO);
	
	public List<BoardDTO> getBoardListRg(BoardDTO boardDTO);
	
	public BoardDTO getBoardContent(BoardDTO boardDTO);
	
	public BoardDTO insertBoardArticle(BoardDTO boardDTO);
	
	public int updateBoardArticle(BoardDTO boardDTO);
	
	public int deleteBoardArticle(BoardDTO boardDTO);
	
	public void increaseHitCount(BoardDTO boardDTO);
	
	public void updateAttachmentBoardArticleIdx(Map<String, Object> map);
}
