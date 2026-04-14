package com.rg.index.dao;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.index.dto.IndexDTO;

@Repository("indexDAO")
public class IndexDAOImpl implements IndexDAO {

	private final Logger logger = LogManager.getLogger(IndexDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.index.IndexMapper";

	@Override
	public void insertAccessLog(IndexDTO indexDTO) {
		
		logger.debug("");
		
		sqlSession.insert(namespace + ".insertAccessLog", indexDTO);
	}

}
