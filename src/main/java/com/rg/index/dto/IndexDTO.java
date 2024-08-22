package com.rg.index.dto;

public class IndexDTO {

	private String userAgent;
	private String xForwardedFor;
	private String proxyClientIp;
	private String remoteAddr;
	private String hash;
	private String referer;
	private String language;
	private String country;
	private String city;
	private String subdivision;
	
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getxForwardedFor() {
		return xForwardedFor;
	}
	public void setxForwardedFor(String xForwardedFor) {
		this.xForwardedFor = xForwardedFor;
	}
	public String getProxyClientIp() {
		return proxyClientIp;
	}
	public void setProxyClientIp(String proxyClientIp) {
		this.proxyClientIp = proxyClientIp;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
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
