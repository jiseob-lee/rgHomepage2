package com.rg.customer.dao;

import java.util.List;
import java.util.Map;

import com.rg.customer.dto.CustomerDTO;

public interface CustomerDAO {
	
	public List<CustomerDTO> getCustomerList(CustomerDTO customerDTO);
	
	public CustomerDTO insertCustomer(CustomerDTO customerDTO);
	
	public int deleteCustomer(Map<String, Integer[]> customerIdxs);
	
	public int updateCustomer(CustomerDTO customerDTO);
}
