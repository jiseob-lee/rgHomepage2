package com.rg2.mssqlsp.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg2.mssqlsp.dao.MssqlSPDAOImpl;

@Service("mssqlSPService")
public class MssqlSPServiceImpl implements MssqlSPService {

	private final Logger logger = LogManager.getLogger(MssqlSPServiceImpl.class);
	
	@Autowired
	private MssqlSPDAOImpl mssqlSPDAO;
	
	@Override
	public void getTable1(Map<String, Object> param) {
		logger.info("++++++++++++++++++++++++++++++++++++++++++");
		mssqlSPDAO.getTable1(param);
	}

}
