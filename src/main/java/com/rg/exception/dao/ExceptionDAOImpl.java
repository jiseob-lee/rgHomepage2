package com.rg.exception.dao;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.exception.dto.ExceptionDTO;

@Repository("exceptionDAO")
public class ExceptionDAOImpl implements ExceptionDAO {

	private final Logger logger = LogManager.getLogger(ExceptionDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.exception.ExceptionMapper";
	
	@Override
	public int insertException(ExceptionDTO exceptionDTO) {
		
		logger.debug("");
		
		return sqlSession.insert(namespace + ".insertException", exceptionDTO);
	}

}
