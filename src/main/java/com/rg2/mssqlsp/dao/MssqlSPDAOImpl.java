package com.rg2.mssqlsp.dao;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.typing.dao.TypingDAOImpl;
import com.rg.typing.dto.TypingParamDTO;

@Repository("mssqlSPDAO")
public class MssqlSPDAOImpl implements MssqlSPDAO {

	private final Logger logger = LogManager.getLogger(MssqlSPDAOImpl.class);

	//@Autowired
	//@Resource(name="sqlSessionTemplate4")
	//private SqlSession sqlSession;
	
	private final String namespace = "com.rg2.mssqlsp.MssqlSPMapper";

	@Override
	public void getTable1(Map<String, Object> param) {
		logger.info("------------------------------------------------");
		logger.info(param);
		List<Map<String, String>> list = null;
		//list = sqlSession.selectList(namespace + ".getTable1", param);
		logger.info(list);
	}

}
