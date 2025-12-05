package com.rg.board.dto;

public class BoardDTO {

	private int boardArticleIdx;
	private int boardIdx;
	private String boardName;
	private String subject;
	private String content;
	private String subjectEng;
	private String contentEng;
	private int hitCount;
	private String dateCreated;
	private String userIdCreated;
	private String userNameCreated;
	private String userNameCreatedEng;
	private String dateModified;
	private String userIdModified;
	private String userNameModified;
	private String userNameModifiedEng;
	private int listLimit;
	private int listOffset;
	private String openYn;
	private String locale;
	//private String csrfToken;
	
	private String searchKind;
	private String searchValue;

	private String requestURI;
	
	public int getBoardArticleIdx() {
		return boardArticleIdx;
	}
	public void setBoardArticleIdx(int boardArticleIdx) {
		this.boardArticleIdx = boardArticleIdx;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
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
	public int getListLimit() {
		return listLimit;
	}
	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}
	public int getListOffset() {
		return listOffset;
	}
	public void setListOffset(int listOffset) {
		this.listOffset = listOffset;
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
	public String getOpenYn() {
		return openYn;
	}
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getSubjectEng() {
		return subjectEng;
	}
	public void setSubjectEng(String subjectEng) {
		this.subjectEng = subjectEng;
	}
	public String getContentEng() {
		return contentEng;
	}
	public void setContentEng(String contentEng) {
		this.contentEng = contentEng;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	/*
	public String getCsrfToken() {
		return csrfToken;
	}
	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}
	*/
	public String getSearchKind() {
		return searchKind;
	}
	public void setSearchKind(String searchKind) {
		this.searchKind = searchKind;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getUserNameCreatedEng() {
		return userNameCreatedEng;
	}
	public void setUserNameCreatedEng(String userNameCreatedEng) {
		this.userNameCreatedEng = userNameCreatedEng;
	}
	public String getUserNameModifiedEng() {
		return userNameModifiedEng;
	}
	public void setUserNameModifiedEng(String userNameModifiedEng) {
		this.userNameModifiedEng = userNameModifiedEng;
	}

	public String getRequestURI() {
		return this.requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public String toString() {
		
		String str = "\n\n";

		str += "boardArticleIdx : " + this.boardArticleIdx + "\n";
		str += "boardIdx : " + this.boardIdx + "\n";
		str += "boardName : " + this.boardName + "\n";
		str += "subject : " + this.subject + "\n";
		//str += "content : " + this.content + "\n";
		str += "subjectEng : " + this.subjectEng + "\n";
		//str += "contentEng : " + this.contentEng + "\n";
		str += "hitCount : " + this.hitCount + "\n";
		str += "dateCreated : " + this.dateCreated + "\n";
		str += "userIdCreated : " + this.userIdCreated + "\n";
		str += "userNameCreated : " + this.userNameCreated + "\n";
		str += "userNameCreatedEng : " + this.userNameCreatedEng + "\n";
		str += "dateModified : " + this.dateModified + "\n";
		str += "userIdModified : " + this.userIdModified + "\n";
		str += "userNameModified : " + this.userNameModified + "\n";
		str += "userNameModifiedEng : " + this.userNameModifiedEng + "\n";
		str += "listLimit : " + this.listLimit + "\n";
		str += "listOffset : " + this.listOffset + "\n";
		str += "openYn : " + this.openYn + "\n";
		str += "locale : " + this.locale + "\n";
		str += "searchKind : " + this.searchKind + "\n";
		str += "searchValue : " + this.searchValue + "\n";
		str += "requestURI : " + this.requestURI + "\n";
		
		str += "\n\n";
		
		return str;
	}
}
