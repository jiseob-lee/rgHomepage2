package com.rg.customer.dto;

public class CustomerDTO {

	private int customerIdx;
	private String customerName;
	private String imageFile;
	private int imageWidth;
	private int imageHeight;
	private String url;
	private String orderNo;
	
	public String getImageFile() {
		return imageFile;
	}
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(int i) {
		this.imageWidth = i;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCustomerIdx() {
		return customerIdx;
	}
	public void setCustomerIdx(int customerIdx) {
		this.customerIdx = customerIdx;
	}

}
