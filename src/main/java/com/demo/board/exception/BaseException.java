package com.demo.board.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class BaseException extends RuntimeException {

	private static final long serialVersionUID = -3005464418522380957L;

	public BaseException(String message) {

		super(message);
	}

	public BaseException() {
		super();
	}

	public abstract HttpStatus getStatusCode();
}