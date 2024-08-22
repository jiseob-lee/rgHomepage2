package com.rg.board.dao;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.board.dto.BoardDTO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {

	private final Logger logger = LogManager.getLogger(BoardDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.board.BoardMapper";
	

	@Override
	public int getBoardListCount(BoardDTO boardDTO) {
		return sqlSession.selectOne(namespace + ".getBoardListCount", boardDTO);
	}

	@Override
	public int getBoardListCountRg(BoardDTO boardDTO) {
		return sqlSession.selectOne(namespace + ".getBoardListCountRg", boardDTO);
	}

	@Override
	public List<BoardDTO> getBoardList(BoardDTO boardDTO) {
		logger.info("# open_yn #### boardDTO.getOpenYn() : " + boardDTO.getOpenYn());
		List<BoardDTO> boardDTOList = sqlSession.selectList(namespace + ".getBoardList", boardDTO);
		return boardDTOList;
	}

	@Override
	public List<BoardDTO> getBoardListRg(BoardDTO boardDTO) {
		List<BoardDTO> boardDTOList = sqlSession.selectList(namespace + ".getBoardListRg", boardDTO);
		return boardDTOList;
	}

	@Override
	public BoardDTO getBoardContent(BoardDTO boardDTO) {
		return sqlSession.selectOne(namespace + ".getBoardContent", boardDTO);
	}

	@Override
	public BoardDTO insertBoardArticle(BoardDTO boardDTO) {
		sqlSession.insert(namespace + ".insertBoardArticle", boardDTO);
		return boardDTO;
	}

	@Override
	public int updateBoardArticle(BoardDTO boardDTO) {
		return sqlSession.update(namespace + ".updateBoardArticle", boardDTO);
	}

	@Override
	public int deleteBoardArticle(BoardDTO boardDTO) {
		return sqlSession.update(namespace + ".deleteBoardArticle", boardDTO);
	}

	@Override
	public void increaseHitCount(BoardDTO boardDTO) {
		sqlSession.update(namespace + ".increaseHitCount", boardDTO);
	}

	@Override
	public void updateAttachmentBoardArticleIdx(Map<String, Object> map) {
		sqlSession.update(namespace + ".updateAttachmentBoardArticleIdx", map);
	}
}
