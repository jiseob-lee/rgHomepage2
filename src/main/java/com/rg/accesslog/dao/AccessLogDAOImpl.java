package com.rg.accesslog.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;

@Repository("accessLogDAO")
public class AccessLogDAOImpl {

	private final Logger logger = LogManager.getLogger(AccessLogDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.accesslog.AccessLogMapper";
	
	public String getArticleSubject(String articleId) {
		
		logger.debug("");
		
		return sqlSession.selectOne(namespace + ".getArticleSubject", articleId);
	}
}
