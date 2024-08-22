package com.rg.customer.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.rg.attachment.service.AttachmentService;
import com.rg.customer.dto.CustomerDTO;

public interface CustomerService {
	
	public List<CustomerDTO> getCustomerList(CustomerDTO customerDTO);
	
	public CustomerDTO insertCustomer(CustomerDTO customerDTO);
	
	public int updateCustomer(List<CustomerDTO> list, int insertedCustomerIdx);

	public static String getFileUploadPath() {
		
		Properties properties = new Properties();
		
		String propertiesFile = "application.properties";
		
		String fileUploadPath = "";
		
        try {
			InputStream is = AttachmentService.class.getClassLoader().getResourceAsStream(propertiesFile);
			properties.load(is);
			fileUploadPath = properties.getProperty("customerUploadPath");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return fileUploadPath;
	}

}
