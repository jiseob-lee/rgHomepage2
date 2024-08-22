package com.rg.statistics.dto;

public class ArticleCountDTO {

	private String articleIdx;
	private String subject;
	private Integer articleCount;
	private Integer countKo;
	private Integer countEn;
	private Integer refererCount;
	
	public String getArticleIdx() {
		return articleIdx;
	}
	public void setArticleIdx(String articleIdx) {
		this.articleIdx = articleIdx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Integer getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}
	public Integer getCountKo() {
		return countKo;
	}
	public void setCountKo(Integer countKo) {
		this.countKo = countKo;
	}
	public Integer getCountEn() {
		return countEn;
	}
	public void setCountEn(Integer countEn) {
		this.countEn = countEn;
	}
	public Integer getRefererCount() {
		return refererCount;
	}
	public void setRefererCount(Integer refererCount) {
		this.refererCount = refererCount;
	}
	
}
