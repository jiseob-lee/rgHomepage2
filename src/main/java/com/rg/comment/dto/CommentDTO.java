package com.rg.comment.dto;

public class CommentDTO {

	private String commentIdx;
	private String content;
	private String dateCreated;
	private String dateModified;
	private String userPassword;
	private String userName;
	private String boardArticleIdx;
	private String parentIdx;
	private int level;
	private String action;
	private String locale;
	private String openYn;
	private String userIp;
	
	public String getCommentIdx() {
		return commentIdx;
	}
	public void setCommentIdx(String commentIdx) {
		this.commentIdx = commentIdx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBoardArticleIdx() {
		return boardArticleIdx;
	}
	public void setBoardArticleIdx(String boardArticleIdx) {
		this.boardArticleIdx = boardArticleIdx;
	}
	public String getParentIdx() {
		return parentIdx;
	}
	public void setParentIdx(String parentIdx) {
		this.parentIdx = parentIdx;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getOpenYn() {
		return openYn;
	}
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
}
