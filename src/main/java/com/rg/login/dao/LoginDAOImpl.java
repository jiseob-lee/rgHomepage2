package com.rg.login.dao;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Resource;
//import jakarta.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {

	private final Logger logger = LogManager.getLogger(LoginDAO.class);

	//@Inject
	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.login.LoginMapper";
	
	public String loginProcess(String userId, String userPw) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("userPassword", userPw);
		
		return sqlSession.selectOne(namespace + ".loginProcess", map);
	}
	
	public String getUserName(String userId) {
		return sqlSession.selectOne(namespace + ".getUserName", userId);
	}
	
	public String getLoginPwEncrypted(String userId) {
		return sqlSession.selectOne(namespace + ".getLoginPwEncrypted", userId);
	}
	
	public void putLoginHistory(Map<String, String> loginMap) {
		sqlSession.insert(namespace + ".putLoginHistory", loginMap);
	}
}
