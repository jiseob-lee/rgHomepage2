package com.rg.slowCityMoviePlay.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.slowCityMoviePlay.dto.SlowCityMoviePlayDTO;

import jakarta.annotation.Resource;

@Repository("slowCityMoviePlayDAO")
public class SlowCityMoviePlayDAOImpl implements SlowCityMoviePlayDAO {

	private final Logger logger = LogManager.getLogger(SlowCityMoviePlayDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.slowCityMoviePlay.SlowCityMoviePlayMapper";
	
	@Override
	public int registSlowCityMoviePlay(SlowCityMoviePlayDTO slowCityMoviePlayDTO) {
		return sqlSession.insert(namespace + ".registSlowCityMoviePlay", slowCityMoviePlayDTO);
	}
}
