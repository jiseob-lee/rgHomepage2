package com.rg.user.service;

import java.util.List;

import com.rg.user.dto.UserDTO;

public interface UserService {

	public List<UserDTO> getUserList();
	
	public int insertUser(UserDTO userDTO);
	
	public int updateUser(UserDTO userDTO);
	
	public int deleteUser(UserDTO userDTO);
	
}
