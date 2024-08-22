package com.rg.user.dao;

import java.util.List;

import com.rg.user.dto.UserDTO;

public interface UserDAO {
	
	public List<UserDTO> getUserList();
	
	public int insertUser(UserDTO userDTO);
	
	public int updateUser(UserDTO userDTO);
	
	public int deleteUser(UserDTO userDTO);
	
}
