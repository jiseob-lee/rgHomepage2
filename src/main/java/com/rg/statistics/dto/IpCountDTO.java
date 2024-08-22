package com.rg.statistics.dto;

public class IpCountDTO {
	
	private String date;
	private String ipCount;
	private String totalCount;
	
	public String getIpCount() {
		return ipCount;
	}
	public void setIpCount(String ipCount) {
		this.ipCount = ipCount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
