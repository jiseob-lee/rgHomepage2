package com.rg.statistics.dao;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.statistics.dto.ArticleCountDTO;
import com.rg.statistics.dto.IpCountDTO;
import com.rg.statistics.dto.LogDTO;

@Repository("statisticsDAO")
public class StatisticsDAOImpl implements StatisticsDAO {

	private final Logger logger = LogManager.getLogger(StatisticsDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.manageBoard.StatisticsMapper";
	

	@Override
	public List<IpCountDTO> getIpCount() {
		return sqlSession.selectList(namespace + ".getStatisticsIpCount", null);
	}


	@Override
	public List<ArticleCountDTO> getStatisticsArticleSubjects(Map<String, String> map) {
		return sqlSession.selectList(namespace + ".getStatisticsArticleSubjects", map);
	}

	@Override
	public List<LogDTO> getStatisticsLog(String date) {
		return sqlSession.selectList(namespace + ".getStatisticsLog", date);
	}
	
}
