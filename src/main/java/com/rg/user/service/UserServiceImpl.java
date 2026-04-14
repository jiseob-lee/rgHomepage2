package com.rg.user.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.user.dao.UserDAOImpl;
import com.rg.user.dto.UserDTO;

@Service("userService")
public class UserServiceImpl implements UserService {

	private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAOImpl userDAO;
	
	@Override
	public List<UserDTO> getUserList() {
		
		logger.debug("");
		
		return userDAO.getUserList();
	}

	@Override
	public int insertUser(UserDTO userDTO) {
		return userDAO.insertUser(userDTO);
	}

	@Override
	public int updateUser(UserDTO userDTO) {
		return userDAO.updateUser(userDTO);
	}

	@Override
	public int deleteUser(UserDTO userDTO) {
		return userDAO.deleteUser(userDTO);
	}

}
