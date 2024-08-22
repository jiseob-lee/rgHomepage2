package com.rg.comment.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.rg.comment.dto.CommentDTO;

public interface CommentService {
	
	public List<CommentDTO> getCommentList(CommentDTO boardDTO);
	
	public void updateComment(CommentDTO boardDTO, HttpServletRequest request);
	
	public void deleteComment(CommentDTO boardDTO);
	
	public void inputComment(CommentDTO boardDTO, HttpServletRequest request);

	public int checkCommentPassword(CommentDTO boardDTO);
}
