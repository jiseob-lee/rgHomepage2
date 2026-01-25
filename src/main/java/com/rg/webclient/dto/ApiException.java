package com.rg.webclient.dto;

public class ApiException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8829647804131822047L;
	
	private final InternalErrorCode errorCode;

    public ApiException(InternalErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiException(InternalErrorCode errorCode) {
        super(errorCode.status().toString());
        this.errorCode = errorCode;
    }

    public InternalErrorCode getErrorCode() {
        return errorCode;
    }
}