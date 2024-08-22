package com.rg.board.service;

import java.util.List;

import com.rg.board.dto.BoardDTO;

public interface BoardService {
	
	public int getBoardListCount(BoardDTO boardDTO);
	
	public int getBoardListCountRg(BoardDTO boardDTO);
	
	public List<BoardDTO> getBoardList(BoardDTO boardDTO);
	
	public List<BoardDTO> getBoardListRg(BoardDTO boardDTO);
	
	public BoardDTO getBoardContent(BoardDTO boardDTO);
	
	public int insertBoardArticle(BoardDTO boardDTO, Integer[] attachmentIdxs);
	
	public int updateBoardArticle(BoardDTO boardDTO, Integer[] attachmentIdxs);
	
	public int deleteBoardArticle(BoardDTO boardDTO);
	
	public void increaseHitCount(BoardDTO boardDTO);
}
