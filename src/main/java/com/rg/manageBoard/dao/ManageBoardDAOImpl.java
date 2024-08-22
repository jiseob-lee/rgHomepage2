package com.rg.manageBoard.dao;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.manageBoard.dto.ManageBoardDTO;

@Repository("manageBoardDAO")
public class ManageBoardDAOImpl implements ManageBoardDAO {

	private final Logger logger = LogManager.getLogger(ManageBoardDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.manageBoard.ManageBoardMapper";
	

	@Override
	public int getManageBoardListCount(ManageBoardDTO boardDTO) {
		return sqlSession.selectOne(namespace + ".getManageBoardListCount", boardDTO);
	}

	@Override
	public List<ManageBoardDTO> getManageBoardList(ManageBoardDTO boardDTO) {
		List<ManageBoardDTO> boardDTOList = sqlSession.selectList(namespace + ".getManageBoardList", boardDTO);
		return boardDTOList;
	}

	@Override
	public ManageBoardDTO createBoard(ManageBoardDTO boardDTO) {
		sqlSession.insert(namespace + ".createBoard", boardDTO);
		return boardDTO;
	}

	@Override
	public int editBoardName(ManageBoardDTO boardDTO) {
		return sqlSession.update(namespace + ".editBoardName", boardDTO);
	}

	/*
	@Override
	public ManageBoardDTO getBoardContent(ManageBoardDTO boardDTO) {
		return sqlSession.selectOne(namespace + ".getBoardContent", boardDTO);
	}

	@Override
	public int updateBoardArticle(ManageBoardDTO boardDTO) {
		return sqlSession.update(namespace + ".updateBoardArticle", boardDTO);
	}

	@Override
	public int deleteBoardArticle(ManageBoardDTO boardDTO) {
		return sqlSession.update(namespace + ".deleteBoardArticle", boardDTO);
	}

	@Override
	public void increaseHitCount(ManageBoardDTO boardDTO) {
		sqlSession.update(namespace + ".increaseHitCount", boardDTO);
	}

	@Override
	public void updateAttachmentBoardArticleIdx(Map<String, Object> map) {
		sqlSession.update(namespace + ".updateAttachmentBoardArticleIdx", map);
	}
	*/
}
