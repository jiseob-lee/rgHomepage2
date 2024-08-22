package com.rg.typing.dto;

public class BibleDTO {

	private String bibleName;
	private Integer bibleChapters;
	private String abbreviationKor;
	private String abbreviationEng;
	private Integer typedChaptersCount;
	
	public String getBibleName() {
		return bibleName;
	}
	public void setBibleName(String bibleName) {
		this.bibleName = bibleName;
	}
	public Integer getBibleChapters() {
		return bibleChapters;
	}
	public void setBibleChapters(Integer bibleChapters) {
		this.bibleChapters = bibleChapters;
	}
	public String getAbbreviationKor() {
		return abbreviationKor;
	}
	public void setAbbreviationKor(String abbreviationKor) {
		this.abbreviationKor = abbreviationKor;
	}
	public String getAbbreviationEng() {
		return abbreviationEng;
	}
	public void setAbbreviationEng(String abbreviationEng) {
		this.abbreviationEng = abbreviationEng;
	}
	public Integer getTypedChaptersCount() {
		return typedChaptersCount;
	}
	public void setTypedChaptersCount(Integer typedChaptersCount) {
		this.typedChaptersCount = typedChaptersCount;
	}
}
