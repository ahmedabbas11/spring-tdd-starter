package com.demo.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException extends BaseException {


	public NotAcceptableException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.NOT_ACCEPTABLE;
	}

}