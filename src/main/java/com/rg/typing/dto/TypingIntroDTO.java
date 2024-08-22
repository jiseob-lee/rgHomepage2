package com.rg.typing.dto;

public class TypingIntroDTO {

	private Integer bibleIndex;
	private String bibleName;
	private Integer bibleChapters;
	private String abbreviationKor;
	private String abbreviationEng;
	private Integer completeCount;
	private Integer percent;
	private Integer completeTotalCount;
	private Integer doingCount;
	private Integer doneCount;
	
	public Integer getBibleIndex() {
		return bibleIndex;
	}
	public void setBibleIndex(Integer bibleIndex) {
		this.bibleIndex = bibleIndex;
	}
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
	public Integer getCompleteCount() {
		return completeCount;
	}
	public void setCompleteCnt(Integer completeCount) {
		this.completeCount = completeCount;
	}
	public Integer getPercent() {
		return percent;
	}
	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	public Integer getCompleteTotalCount() {
		return completeTotalCount;
	}
	public void setCompleteTotalCount(Integer completeTotalCount) {
		this.completeTotalCount = completeTotalCount;
	}
	public Integer getDoingCount() {
		return doingCount;
	}
	public void setDoingCount(Integer doingCount) {
		this.doingCount = doingCount;
	}
	public Integer getDoneCount() {
		return doneCount;
	}
	public void setDoneCount(Integer doneCount) {
		this.doneCount = doneCount;
	}
	
}
