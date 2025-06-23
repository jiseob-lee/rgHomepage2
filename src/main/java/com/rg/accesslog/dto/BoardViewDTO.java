package com.rg.accesslog.dto;

public class BoardViewDTO {
	
	private String ipAddress;
	private String dateTime;
	private String method;
	private String requestURI;
	private String protocol;
	private String statusCode;
	private String responseSize;
	private String referer;
	private String userAgent;
	private String country;
	private String subdivision;
	private String city;
	private String articleSubject;
	
	public String getLogTime() {
		String ymd = this.dateTime.substring(0, this.dateTime.indexOf(":"));
		String tmpStr = this.dateTime.substring(this.dateTime.indexOf(":"));
		String timeStr = tmpStr.substring(1, tmpStr.indexOf(" "));
		return ymd + " " + timeStr;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getResponseSize() {
		return responseSize;
	}
	public void setResponseSize(String responseSize) {
		this.responseSize = responseSize;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArticleSubject() {
		return articleSubject;
	}
	public void setArticleSubject(String articleSubject) {
		this.articleSubject = articleSubject;
	}
	
}
