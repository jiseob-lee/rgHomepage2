package com.rg.comment.dao;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rg.comment.dto.CommentDTO;

@Repository("commentDAO")
public class CommentDAOImpl implements CommentDAO {

	//private final Logger logger = LogManager.getLogger(BoardDAOImpl.class);

	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSession;
	
	private final String namespace = "com.rg.comment.CommentMapper";
	

	@Override
	public List<CommentDTO> getCommentList(CommentDTO commentDTO) {
		List<CommentDTO> commentDTOList = sqlSession.selectList(namespace + ".getCommentList", commentDTO);
		return commentDTOList;
	}

	@Override
	public void updateComment(CommentDTO commentDTO) {
		sqlSession.update(namespace + ".updateComment", commentDTO);
	}

	@Override
	public void deleteComment(CommentDTO commentDTO) {
		sqlSession.update(namespace + ".deleteComment", commentDTO);
	}

	@Override
	public void inputComment(CommentDTO commentDTO) {
		sqlSession.insert(namespace + ".inputComment", commentDTO);
	}

	@Override
	public int checkCommentPassword(CommentDTO commentDTO) {
		return sqlSession.selectOne(namespace + ".checkCommentPassword", commentDTO);
	}
	
	@Override
	public int getCommentTotalCount() {
		return sqlSession.selectOne(namespace + ".getCommentTotalCount", null);
	}
	
	@Override
	public List<CommentDTO> getCommentTotalList(Map<String, Object> map) {
		List<CommentDTO> commentDTOTotalList = sqlSession.selectList(namespace + ".getCommentTotalList", map);
		return commentDTOTotalList;
	}
	
}
