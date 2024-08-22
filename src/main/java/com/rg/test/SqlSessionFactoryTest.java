package com.rg.test;

import jakarta.inject.Inject;

import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;

public class SqlSessionFactoryTest extends AbstractTest {

	@Inject
	SqlSessionFactoryBean factoryBean;
	
	@Test
	public void test() {
		logger.info("" + factoryBean);
	}
	
	@Test
	public void sessionTest() {
		try {
			logger.info("" + factoryBean.getObject().openSession().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
