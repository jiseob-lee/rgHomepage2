package com.rg.loginlog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.loginlog.dto.LoginLogDTO;

import jakarta.annotation.Resource;

@Repository("loginLogDAO")
public class LoginLogDAOImpl implements LoginLogDAO {

	private final Logger logger = LogManager.getLogger(LoginLogDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.loginlog.LoginLogMapper";
	
	@Override
	public int getLoginLogCount() {
		
		logger.debug("");
		
		return sqlSession.selectOne(namespace + ".getLoginLogCount");
	}
	
	@Override
	public List<LoginLogDTO> getLoginLogList(Map<String, Object> map) {
		return sqlSession.selectList(namespace + ".getLoginLogList", map);
	}


}
