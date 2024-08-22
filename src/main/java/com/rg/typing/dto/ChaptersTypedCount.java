package com.rg.typing.dto;

public class ChaptersTypedCount {

	private String abbreviationEng;
	private int chapter;
	private int cnt;
	
	public String getAbbreviationEng() {
		return abbreviationEng;
	}
	public void setAbbreviationEng(String abbreviationEng) {
		this.abbreviationEng = abbreviationEng;
	}
	public int getChapter() {
		return chapter;
	}
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public String toString() {
		return "abbreviationEng : " + this.abbreviationEng + ", chapter : " + this.chapter + ", cnt : " + this.cnt;
	}
}
