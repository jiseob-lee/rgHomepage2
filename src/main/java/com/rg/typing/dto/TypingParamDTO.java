package com.rg.typing.dto;

public class TypingParamDTO {
	
	private String loginId;
	private Integer charCount;
	private Integer timeElapsed;
	private String timeElapsedString;
	private String timeCreated;
	private Integer average;
	private String title;
	private Integer pageNo;
	private String phrase;
	private Integer chapter;
	private Integer typingLength;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public Integer getCharCount() {
		return charCount;
	}
	public void setCharCount(Integer charCount) {
		this.charCount = charCount;
	}
	public Integer getAverage() {
		return average;
	}
	public void setAverage(Integer average) {
		this.average = average;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public Integer getChapter() {
		return chapter;
	}
	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}
	public Integer getTimeElapsed() {
		return timeElapsed;
	}
	public void setTimeElapsed(Integer timeElapsed) {
		this.timeElapsed = timeElapsed;
	}
	public String getTimeElapsedString() {
		return timeElapsedString;
	}
	public void setTimeElapsedString(String timeElapsedString) {
		this.timeElapsedString = timeElapsedString;
	}
	
	public Integer getTypingLength() {
		return typingLength;
	}
	public void setTypingLength(Integer typingLength) {
		this.typingLength = typingLength;
	}

	public String toString() {
		
		String returnStr = "";

		returnStr += "loginId : " + this.loginId;
		
		returnStr += ", charCount : " + this.charCount;
		
		returnStr += ", timeElapsed : " + this.timeElapsed;
		
		returnStr += ", timeElapsedString : " + this.timeElapsedString;
		
		returnStr += ", timeCreated : " + this.timeCreated;
		
		returnStr += ", average : " + this.average;
		
		returnStr += ", title : " + this.title;
		
		returnStr += ", pageNo : " + this.pageNo;
		
		returnStr += ", phrase : " + this.phrase;
		
		returnStr += ", chapter : " + this.chapter;
		
		returnStr += ", typingLength : " + this.typingLength;
		
		return returnStr;
	}
}
