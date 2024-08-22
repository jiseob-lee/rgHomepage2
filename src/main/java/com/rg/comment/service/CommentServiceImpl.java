package com.rg.comment.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rg.comment.dao.CommentDAOImpl;
import com.rg.comment.dto.CommentDTO;
import com.rg.util.IP;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	private final Logger logger = LogManager.getLogger(CommentServiceImpl.class);

	@Autowired
	private CommentDAOImpl commentDAO;

	@Override
	public List<CommentDTO> getCommentList(CommentDTO commentDTO) {
		return commentDAO.getCommentList(commentDTO);
	}

	@Override
	public void updateComment(CommentDTO commentDTO, HttpServletRequest request) {
		String ip = IP.getClientIP(request);
		logger.debug("#### ip : " + ip);
		commentDTO.setUserIp(ip);
		commentDAO.updateComment(commentDTO);
	}

	@Override
	public void deleteComment(CommentDTO commentDTO) {
		commentDAO.deleteComment(commentDTO);
	}

	@Override
	public void inputComment(CommentDTO commentDTO, HttpServletRequest request) {
		String ip = IP.getClientIP(request);
		logger.debug("#### ip : " + ip);
		commentDTO.setUserIp(ip);
		commentDAO.inputComment(commentDTO);
	}
	
	@Override
	public int checkCommentPassword(CommentDTO commentDTO) {
		return commentDAO.checkCommentPassword(commentDTO);
	}
	
}
