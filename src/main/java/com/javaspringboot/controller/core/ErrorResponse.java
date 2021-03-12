package com.javaspringboot.controller.core;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private Integer status_code;
	private String message;

	public ErrorResponse(Integer status_code, String message) {
		super();
		this.status_code = status_code;
		this.message = message;
	}

	public ErrorResponse(String message) {
		super();
		this.status_code = HttpStatus.BAD_REQUEST.value();
		this.message = message;
	}

	public Integer getStatus_code() {
		return status_code;
	}

	public void setStatus_code(Integer status_code) {
		this.status_code = status_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}