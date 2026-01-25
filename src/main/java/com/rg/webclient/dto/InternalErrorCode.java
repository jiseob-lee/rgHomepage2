package com.rg.webclient.dto;

import org.springframework.http.HttpStatus;

public enum InternalErrorCode {

    INVALID_REQUEST("C001", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("C002", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("C003", HttpStatus.FORBIDDEN),
    NOT_FOUND("C004", HttpStatus.NOT_FOUND),

    EXTERNAL_TIMEOUT("E001", HttpStatus.GATEWAY_TIMEOUT),
    EXTERNAL_UNAVAILABLE("E002", HttpStatus.BAD_GATEWAY),
    EXTERNAL_ERROR("E999", HttpStatus.BAD_GATEWAY);

    private final String code;
    private final HttpStatus status;

    InternalErrorCode(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    public String code() {
        return code;
    }

    public HttpStatus status() {
        return status;
    }
}