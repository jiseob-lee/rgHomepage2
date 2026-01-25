package com.rg.webclient.dto;

public record ApiResponse(
	String resultCode,
	String message,
	String data
) {
	
}
