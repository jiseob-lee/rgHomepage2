package com.rg.downhistory.dto;

public class DownHistoryDTO {
	
	private String attachment_idx;
	private String board_idx;
	private String board_name;
	private String board_article_idx;
	private String subject;
	private String server_file_name;
	private String attachment_name;
	private String attachment_ext;
	private int attachment_size;
	private String date_created;
	private String user_ip;
	private String user_country;
	private String user_subdivision;
	private String user_city;
	
	
	public String getFileName() {
		return attachment_name + "." + attachment_ext;
	}
	
	public String getAttachment_idx() {
		return attachment_idx;
	}
	public void setAttachment_idx(String attachment_idx) {
		this.attachment_idx = attachment_idx;
	}
	public String getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(String board_idx) {
		this.board_idx = board_idx;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public String getBoard_article_idx() {
		return board_article_idx;
	}
	public void setBoard_article_idx(String board_article_idx) {
		this.board_article_idx = board_article_idx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getServer_file_name() {
		return server_file_name;
	}
	public void setServer_file_name(String server_file_name) {
		this.server_file_name = server_file_name;
	}
	public String getAttachment_name() {
		return attachment_name;
	}
	public void setAttachment_name(String attachment_name) {
		this.attachment_name = attachment_name;
	}
	public String getAttachment_ext() {
		return attachment_ext;
	}
	public void setAttachment_ext(String attachment_ext) {
		this.attachment_ext = attachment_ext;
	}
	public int getAttachment_size() {
		return attachment_size;
	}
	public void setAttachment_size(int attachment_size) {
		this.attachment_size = attachment_size;
	}
	public String getDate_created() {
		return date_created;
	}
	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public String getUser_country() {
		return user_country;
	}
	public void setUser_country(String user_country) {
		this.user_country = user_country;
	}
	public String getUser_subdivision() {
		return user_subdivision;
	}
	public void setUser_subdivision(String user_subdivision) {
		this.user_subdivision = user_subdivision;
	}
	public String getUser_city() {
		return user_city;
	}
	public void setUser_city(String user_city) {
		this.user_city = user_city;
	}

	
}
