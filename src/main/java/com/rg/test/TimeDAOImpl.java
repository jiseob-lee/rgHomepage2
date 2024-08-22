package com.rg.test;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TimeDAOImpl implements TimeDAO {

	//@Inject
	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.test.TestMapper";
	
	public String getTime() {
		return sqlSession.selectOne(namespace + ".getNow");
	}
}
