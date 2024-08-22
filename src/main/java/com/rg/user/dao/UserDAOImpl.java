package com.rg.user.dao;

import java.util.List;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.user.dto.UserDTO;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	private final Logger logger = LogManager.getLogger(UserDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.user.UserMapper";
	
	@Override
	public List<UserDTO> getUserList() {
		List<UserDTO> userList = null;
		try {
			userList = sqlSession.selectList(namespace + ".getUserList");
			logger.error("userList size: " + userList.size());
			UserDTO userDTO = userList.get(0);
			logger.error("username ko : " + userDTO.getUserNameKo());
		
		} catch (Exception e) {
			logger.error("error message : " + e.getMessage());
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public int insertUser(UserDTO userDTO) {
		return sqlSession.insert(namespace + ".insertUser", userDTO);
	}

	@Override
	public int updateUser(UserDTO userDTO) {
		return sqlSession.update(namespace + ".updateUser", userDTO);
	}

	@Override
	public int deleteUser(UserDTO userDTO) {
		return sqlSession.update(namespace + ".deleteUser", userDTO);
	}

}
