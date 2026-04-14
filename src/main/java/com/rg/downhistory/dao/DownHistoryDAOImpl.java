package com.rg.downhistory.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.loginlog.dto.LoginLogDTO;

import jakarta.annotation.Resource;

@Repository("downHistoryDAO")
public class DownHistoryDAOImpl implements DownHistoryDAO {

	private final Logger logger = LogManager.getLogger(DownHistoryDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.downhistory.DownHistoryMapper";
	
	@Override
	public int getDownHistoryCount() {
		
		logger.debug("");
		
		return sqlSession.selectOne(namespace + ".getDownHistoryCount");
	}
	
	@Override
	public List<LoginLogDTO> getDownHistoryList(Map<String, Object> map) {
		return sqlSession.selectList(namespace + ".getDownHistoryList", map);
	}


}
