package com.rg.manageBoard.dto;

public class ManageBoardDTO {

	private int boardIdx;
	private String boardName;
	private String dateCreated;
	private String userIdCreated;
	private String userNameCreated;
	private String dateModified;
	private String userIdModified;
	private String userNameModified;
	private int articleCount;
	private String attachmentCount;
	private String openYn;
	private String locale;
	
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getUserIdCreated() {
		return userIdCreated;
	}
	public void setUserIdCreated(String userIdCreated) {
		this.userIdCreated = userIdCreated;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public String getUserIdModified() {
		return userIdModified;
	}
	public void setUserIdModified(String userIdModified) {
		this.userIdModified = userIdModified;
	}
	public String getUserNameCreated() {
		return userNameCreated;
	}
	public void setUserNameCreated(String userNameCreated) {
		this.userNameCreated = userNameCreated;
	}
	public String getUserNameModified() {
		return userNameModified;
	}
	public void setUserNameModified(String userNameModified) {
		this.userNameModified = userNameModified;
	}
	public int getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	public String getAttachmentCount() {
		return attachmentCount;
	}
	public void setAttachmentCount(String attachmentCount) {
		this.attachmentCount = attachmentCount;
	}
	public String getOpenYn() {
		return openYn;
	}
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}

}
