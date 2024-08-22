package com.rg.statistics.dto;

public class LogDTO {
	private int logIndex;
	private String userAgent;
	private String ip;
	private String logTime;
	private String path;
	private int articleIdx;
	private String subject;
	private String language;
	private String referer;
	private String country;
	private String city;
	private String subdivision;
	
	public int getLogIndex() {
		return logIndex;
	}
	public void setLogIndex(int logIndex) {
		this.logIndex = logIndex;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getArticleIdx() {
		return articleIdx;
	}
	public void setArticleIdx(int articleIdx) {
		this.articleIdx = articleIdx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	
}
