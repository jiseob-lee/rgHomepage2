package com.rg.customer.dao;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.customer.dto.CustomerDTO;

@Repository("customerDAO")
public class CustomerDAOImpl implements CustomerDAO {

	private final Logger logger = LogManager.getLogger(CustomerDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.customer.CustomerMapper";
	

	@Override
	public List<CustomerDTO> getCustomerList(CustomerDTO customerDTO) {
		return sqlSession.selectList(namespace + ".getCustomerList", customerDTO);
	}

	@Override
	public CustomerDTO insertCustomer(CustomerDTO customerDTO) {
		sqlSession.insert(namespace + ".insertCustomer", customerDTO);
		return customerDTO;
	}

	@Override
	public int deleteCustomer(Map<String, Integer[]> customeridxs) {
		return sqlSession.update(namespace + ".deleteCustomer", customeridxs);
	}

	@Override
	public int updateCustomer(CustomerDTO customerDTO) {
		return sqlSession.update(namespace + ".updateCustomer", customerDTO);
	}
}
