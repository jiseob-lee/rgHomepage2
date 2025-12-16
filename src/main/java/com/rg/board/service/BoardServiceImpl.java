package com.rg.board.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.board.dao.BoardDAOImpl;
import com.rg.board.dto.BoardDTO;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

	private final Logger logger = LogManager.getLogger(BoardServiceImpl.class);

	@Autowired
	private BoardDAOImpl boardDAO;
	

	@Override
	public int getBoardListCount(BoardDTO boardDTO) {
		return boardDAO.getBoardListCount(boardDTO);
	}

	@Override
	public int getBoardListCountRg(BoardDTO boardDTO) {
		return boardDAO.getBoardListCountRg(boardDTO);
	}

	@Override
	public List<BoardDTO> getBoardList(BoardDTO boardDTO) {
		return boardDAO.getBoardList(boardDTO);
	}

	@Override
	public List<BoardDTO> getBoardListRg(BoardDTO boardDTO) {
		return boardDAO.getBoardListRg(boardDTO);
	}

	@Override
	public BoardDTO getBoardContent(BoardDTO boardDTO) {
		
		logger.info("#### boardDTO : " + boardDTO.toString());
		
		String requestURI = boardDTO.getRequestURI();
		
		BoardDTO returnDTO = boardDAO.getBoardContent(boardDTO);
		
		String openYn = returnDTO == null ? "N" : returnDTO.getOpenYn();
		
		if (requestURI != null && !requestURI.startsWith("/rg") && openYn != null && !openYn.equalsIgnoreCase("Y")) {
			return new BoardDTO();
		}
		
		logger.info("#### returnDTO : " + returnDTO == null ? "" : returnDTO.toString());
		
		return returnDTO;
	}

	@Override
	public int insertBoardArticle(BoardDTO boardDTO, Integer[] attachmentIdxs) {
		boardDTO = boardDAO.insertBoardArticle(boardDTO);

		if (attachmentIdxs != null && attachmentIdxs.length > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("boardArticleIdx", boardDTO.getBoardArticleIdx());
			map.put("attachmentIdxs", attachmentIdxs);
			boardDAO.updateAttachmentBoardArticleIdx(map);
		}
		
		return 1;
	}

	@Override
	public int updateBoardArticle(BoardDTO boardDTO, Integer[] attachmentIdxs) {
		int result = boardDAO.updateBoardArticle(boardDTO);
		if (attachmentIdxs != null && attachmentIdxs.length > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("boardArticleIdx", boardDTO.getBoardArticleIdx());
			map.put("boardIdx", boardDTO.getBoardIdx());
			map.put("attachmentIdxs", attachmentIdxs);
			boardDAO.updateAttachmentBoardArticleIdx(map);
		}
		return result;
	}

	@Override
	public int deleteBoardArticle(BoardDTO boardDTO) {
		return boardDAO.deleteBoardArticle(boardDTO);
	}

	@Override
	public void increaseHitCount(BoardDTO boardDTO) {
		boardDAO.increaseHitCount(boardDTO);
	}

}
