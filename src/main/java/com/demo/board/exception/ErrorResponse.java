package com.demo.board.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

	private String message;

	public ErrorResponse() {
	}

	public ErrorResponse(String message) {
		this.setMessage(message);
	}

}