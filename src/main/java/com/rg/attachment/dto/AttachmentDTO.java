package com.rg.attachment.dto;

public class AttachmentDTO {

	private String attachmentIdx;
	private String boardIdx;
	private String boardArticleIdx;
	private String serverFileName;
	private String attachmentName;
	private String attachmentExt;
	private long attachmentSize;
	private int attachmentOrder;
	private String dateCreated;
	private String userIdCreated;
	private String userNameCreated;
	private String dateModified;
	private String userIdModified;
	private String userNameModified;
	private int flagDeleted;
	private int fileExists;
	private int listLimit;
	private int listOffset;
	private String openYn;
	private String userIp;
	private String userCountry;
	private String userSubdivision;
	private String userCity;
	
	private String requestURI;
	
	public String getAttachmentIdx() {
		return attachmentIdx;
	}
	public void setAttachmentIdx(String attachmentIdx) {
		this.attachmentIdx = attachmentIdx;
	}
	public String getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(String boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getBoardArticleIdx() {
		return boardArticleIdx;
	}
	public void setBoardArticleIdx(String boardArticleIdx) {
		this.boardArticleIdx = boardArticleIdx;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getAttachmentExt() {
		return attachmentExt;
	}
	public void setAttachmentExt(String attachmentExt) {
		this.attachmentExt = attachmentExt;
	}
	public int getAttachmentOrder() {
		return attachmentOrder;
	}
	public void setAttachmentOrder(int attachmentOrder) {
		this.attachmentOrder = attachmentOrder;
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
	public int getFlagDeleted() {
		return flagDeleted;
	}
	public void setFlagDeleted(int flagDeleted) {
		this.flagDeleted = flagDeleted;
	}
	public int getFileExists() {
		return fileExists;
	}
	public void setFileExists(int fileExists) {
		this.fileExists = fileExists;
	}
	public String getServerFileName() {
		return serverFileName;
	}
	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}
	public long getAttachmentSize() {
		return attachmentSize;
	}
	public void setAttachmentSize(long attachmentSize) {
		this.attachmentSize = attachmentSize;
	}
	public int getListLimit() {
		return listLimit;
	}
	public void setListLimit(String listLimit) {
		this.listLimit = Integer.valueOf(listLimit).intValue();
	}
	public int getListOffset() {
		return listOffset;
	}
	public void setListOffset(String listOffset) {
		this.listOffset = Integer.valueOf(listOffset).intValue();
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
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getUserCountry() {
		return userCountry;
	}
	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public String getUserSubdivision() {
		return userSubdivision;
	}
	public void setUserSubdivision(String userSubdivision) {
		this.userSubdivision = userSubdivision;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

}
