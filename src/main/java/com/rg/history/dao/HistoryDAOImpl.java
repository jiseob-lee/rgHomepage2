package com.rg.history.dao;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.history.dto.HistoryDTO;

@Repository("historyDAO")
public class HistoryDAOImpl implements HistoryDAO {

	private final Logger logger = LogManager.getLogger(HistoryDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.history.HistoryMapper";
	

	@Override
	public List<HistoryDTO> getHistoryList(HistoryDTO historyDTO) {
		
		logger.debug("");
		
		return sqlSession.selectList(namespace + ".getHistoryList", historyDTO);
	}

	@Override
	public int insertHistory(HistoryDTO historyDTO) {
		return sqlSession.insert(namespace + ".insertHistory", historyDTO);
	}

	@Override
	public int deleteHistory(int year) {
		return sqlSession.update(namespace + ".deleteHistory", year);
	}

}
