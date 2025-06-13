package com.rg.comment.dao;

import java.util.List;
import java.util.Map;

import com.rg.comment.dto.CommentDTO;

public interface CommentDAO {

	public List<CommentDTO> getCommentList(CommentDTO boardDTO);
	
	public void updateComment(CommentDTO boardDTO);
	
	public void deleteComment(CommentDTO boardDTO);
	
	public void inputComment(CommentDTO boardDTO);
	
	public int checkCommentPassword(CommentDTO boardDTO);
	
	public List<CommentDTO> getCommentTotalList(Map<String, Object> map);
	
	public int getCommentTotalCount();
}
