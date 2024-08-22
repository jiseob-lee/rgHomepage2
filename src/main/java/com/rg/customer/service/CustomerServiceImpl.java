package com.rg.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.customer.dao.CustomerDAOImpl;
import com.rg.customer.dto.CustomerDTO;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	private final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDAOImpl customerDAO;
	

	@Override
	public List<CustomerDTO> getCustomerList(CustomerDTO customerDTO) {
		return customerDAO.getCustomerList(customerDTO);
	}

	@Override
	public CustomerDTO insertCustomer(CustomerDTO customerDTO) {
		return customerDAO.insertCustomer(customerDTO);
	}
	
	@Override
	public int updateCustomer(List<CustomerDTO> list, int insertedCustomerIdx) {
		
		int j = 0;
		
		CustomerDTO customerDTO = null;
		
		Integer[] customerIdxs = new Integer[list.size() + 2];
		customerIdxs[0] = 0;
		customerIdxs[1] = insertedCustomerIdx;
		if (list != null && list.size() > 0) {
			logger.debug("#################### updateCustomer : size : " + list.size());
			//List<Integer> l = new ArrayList<Integer>();
			for (int i=0; i<list.size(); i++) {
				logger.debug("#################### updateCustomer : i : " + i);
				customerDTO = list.get(i);
				j += customerDAO.updateCustomer(customerDTO);
				//l.add(customerDTO.getCustomerIdx());
				customerIdxs[i + 2] = customerDTO.getCustomerIdx();
			}
		}

		Map<String, Integer[]> map = new HashMap<String, Integer[]>();
		map.put("customerIdxs", customerIdxs);
		
		customerDAO.deleteCustomer(map);
		
		return j;
	}
}
